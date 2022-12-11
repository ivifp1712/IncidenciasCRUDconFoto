package com.teoria.resumencrud

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import java.io.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var frame: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edition, container, false)

        //bundle
        val bundle = this.arguments
        val codigoA = view.findViewById<EditText>(R.id.et_codigo)
        val nombreA = view.findViewById<EditText>(R.id.et_nombre)
        val descripcionA = view.findViewById<EditText>(R.id.et_facturacion)
        frame = view.findViewById<ImageView>(R.id.foto)
        if (bundle != null) {
            val codigo = bundle.getString("codigo")
            val nombre = bundle.getString("nombre")
            val descripcion = bundle.getString("descripcion")
            val codigoA = view.findViewById<EditText>(R.id.et_codigo)
            val nombreA = view.findViewById<EditText>(R.id.et_nombre)
            val descripcionA = view.findViewById<EditText>(R.id.et_facturacion)
            codigoA.setText(codigo)
            nombreA.setText(nombre)
            descripcionA.setText(descripcion)
            //byteArray to ImageView
            val byteArray = bundle.getByteArray("foto")
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            frame!!.setImageBitmap(bmp)
        }

        var boton = view.findViewById<Button>(R.id.button)
        boton.setOnClickListener(){
            var dataBase1:DbHelper= DbHelper(requireContext())
            val foto = uriToByteArray(frame)
            //print byte array
            dataBase1.editIncidencia(codigoA.text.toString(),nombreA.text.toString(),descripcionA.text.toString(), foto)
            // toast
            Toast.makeText(requireContext(), "Incidencia editada", Toast.LENGTH_SHORT).show()
            var transaction= (context as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, BlankFragment2())
            transaction.commit()
        }

        var boton2 = view.findViewById<Button>(R.id.button_foto)
        boton2.setOnClickListener(){
            // toast
            Toast.makeText(requireContext(), "Abriendo camara...", Toast.LENGTH_SHORT).show()
            openCamera()
        }

        return view
    }

    private fun uriToByteArray(frame: ImageView?): ByteArray {
        var bitmap:Bitmap=frame!!.drawable.toBitmap()
        var stream:ByteArrayOutputStream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
        var byteArray:ByteArray=stream.toByteArray()
        return byteArray
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    var image_uri: Uri? = null
    private val RESULT_LOAD_IMAGE = 123
    val IMAGE_CAPTURE_CODE = 654
    var fileName: String = "photo"
    // cambiar texto textview2
    var mybitmap = null

    private fun saveImg(bitmap: Bitmap) {
        val file = getDisc()
        //val file = File(Environment.DIRECTORY_PICTURES, "Imagenes favoritas")
        if (!file.exists() && !file.mkdirs()) {
            file.mkdir()
        }

        val milisegundos = System.currentTimeMillis()
        val name = "foto_$milisegundos.jpg"
        this.fileName = file.absolutePath + "/" + name
        val newFile = File(fileName)
        // fileName path
        println("fileName path: $fileName")

        try {
            val fileOutPutStream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
            Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show()
            var savedFile = newFile
            fileOutPutStream.flush()
            fileOutPutStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getDisc(): File {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(file, "Imagenes favoritas")
    }


    //TODO opens camera so that user can capture image
    private fun openCamera() {
        println("Open camera")
        val values = ContentValues()
        // String milisegundos hora actual
        val milisegundos = System.currentTimeMillis()
        values.put(MediaStore.Images.Media.TITLE, "foto_$milisegundos.jpg")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Desde la camaritaaaa")
        image_uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            //imageView.setImageURI(image_uri);
            println("image_uri: $image_uri")
            val bitmap = uriToBitmap(image_uri!!)
            println("Bitmap: ${bitmap}")
            frame?.setImageBitmap(bitmap)
            if (bitmap != null) {
                saveImg(bitmap)
                //mybitmap = bitmap
            }

        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            image_uri = data.data
            //imageView.setImageURI(image_uri);
            val bitmap = uriToBitmap(image_uri!!)
            frame?.setImageBitmap(bitmap)
        }

    }
    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }
    //TODO takes URI of the image and returns bitmap
    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? = requireActivity().contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}