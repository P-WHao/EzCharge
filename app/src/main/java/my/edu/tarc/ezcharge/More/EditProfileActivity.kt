package my.edu.tarc.ezcharge.More

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityEditProfileBinding
import java.lang.Exception

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var imageUri: Uri? =null

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        binding.profileIv.setOnClickListener{
            showImageAttachMenu()
        }

        binding.updateBtn.setOnClickListener{
            validateData()
        }

    }

    private var name = ""
    private var email = ""
    private fun validateData() {
        name = binding.nameEt.text.toString().trim()
//        email = binding.emailEt.text.toString().trim()

        if (name.isEmpty()){
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
        }
//        else if (email.isEmpty()){
//            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show()
//        }
        else {
            if (imageUri == null){
                //update without image
                updateProfile("")

            }else{
                //update with image
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        progressDialog.setMessage("Uploading profile image..")
        progressDialog.show()

        val filePathAndName = "ProfileImages/" +firebaseAuth.uid

        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while(!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                updateProfile(uploadedImageUrl)
            }
            .addOnFailureListener{ e->

                progressDialog.dismiss()
                Toast.makeText(this, "Upload image has failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProfile(uploadedImageUrl: String) {
        progressDialog.setMessage("Updating Profile")

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["name"] = "$name"
//        hashMap["email"] = "$email"
        if (imageUri != null){
            hashMap["profileImage"] = uploadedImageUrl
        }

        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile update successfully!", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "Upload image has failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //get user info

                    val name = "${snapshot.child("name").value}"
//                    val email = "${snapshot.child("email").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"




                    //set data
                    binding.nameEt.setText(name)
//                    binding.emailEt.setText(email)


                    //set image
                    try{
                        Glide.with(this@EditProfileActivity)
                            .load(profileImage).placeholder(R.drawable.ic_person_gray)
                            .into(binding.profileIv)
                    }catch (e: Exception){

                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun showImageAttachMenu (){
        /* Choose from camera or gallery*/

        val popupMenu = PopupMenu(this, binding.profileIv)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")
        popupMenu.show()


        popupMenu.setOnMenuItemClickListener { item->

            val id = item.itemId
            if (id == 0 ){
                pickFromCamera()
            }else if (id == 1){
                pickFromGallery()
            }

            true
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback <ActivityResult>{ result ->

            if (result.resultCode == RESULT_OK){
                val data = result.data
                imageUri = data!!.data

                binding.profileIv.setImageURI(imageUri)
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->

            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data


                binding.profileIv.setImageURI(imageUri)
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )
}