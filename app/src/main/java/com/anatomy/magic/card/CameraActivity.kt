package com.anatomy.magic.card

// SettingsFragment.kt
import android.os.Bundle
import android.view.WindowManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        previewView = findViewById(R.id.viewFinder)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
        val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.bindToLifecycle(this, cameraSelector, preview)
    }

    private fun checkPermission(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}