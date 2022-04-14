package my.edu.tarc.ezcharge.PumpCharging

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityChargingScanBinding

private const val CAMERA_REQUEST_CODE = 101;

class ChargingScanActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityChargingScanBinding

    //Scanner
    private lateinit var codeScanner: CodeScanner

    val extras = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val extras = intent.extras

        val walletBalance = extras!!.getDouble("WALLET_BALANCE", 0.00)
        val userPin = extras!!.getString("USER_PIN", "")
        val userID = extras!!.getString("USER_ID").toString()

        setupPermission()
        codeScanner()

        //Call back (Result from decode qr code)
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread{
                //binding.textViewChargingScan.text = it.text
                val textResult = it.text.toString()
                val textID : TextView = binding.textViewChargingScan

                if(textResult.take(3) == "E-C"){
                    textID.text = textResult
                }else{
                    textID.setText(R.string.not_code)
                    Toast.makeText(this, getString(R.string.not_code), Toast.LENGTH_SHORT).show()
                }

                //Button proceed to charging menu
                binding.buttonProceed.setOnClickListener {

                    if(textResult.take(3) == "E-C"){
                        val intent = Intent(this, ChargingPumpActivity::class.java)
                        extras.putDouble("WALLET_BALANCE", walletBalance)
                        extras.putString("itemCode", textResult)
                        extras.putString("USER_PIN", userPin)
                        extras.putString("USER_ID", userID)
                        intent.putExtras(extras)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, getString(R.string.scan_again), Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }


    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner.apply {
            //Which side of camera
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            //Auto focus at fix interval
            autoFocusMode = AutoFocusMode.SAFE
            //Continuos to scan and try to find a qr code or barcode
            scanMode = ScanMode.SINGLE //can be CONTINUOUS or PREVIEW
            isAutoFocusEnabled = true
            isFlashEnabled = false



            //Error call back (When somethings go wrong)
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        //Tap the camera area to continue scan
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    //when we leave the app then came back, it will try to fetch new QR code or barcode
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    //release resources, avoid memory load
    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    //for API 23+ to grant run time permissions
    private fun setupPermission() {
        val permission = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA)

        //check user accept or not
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.CAMERA),
        CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.ask_camera_permission), Toast.LENGTH_SHORT).show()
                }else{
                    //successful
                }
            }
        }
    }
}