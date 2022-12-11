package com.teoria.resumencrud

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        val view = inflater.inflate(R.layout.fragment_blank2, container, false)
        // Inflate the layout for this fragment
        var dataBase1:DbHelper= DbHelper(requireContext())
        val recyclerView: RecyclerView = view.findViewById(R.id.rv)
        //RV : asignarle un adapter y layout
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        var incidencias=ArrayList<Incidencias>()
        //dataBase1.onUpgrade(dataBase1.writableDatabase, 1, 2)
        incidencias=dataBase1.viewIncidencia()//devuelve un AL
        recyclerView.adapter = Adaptador(requireContext(),incidencias)
        //dataBase1 llamar a la función select de SQLite
        //val dataBase:DbHelper=DbHelper(this)//crea un objeto Database y la base de datos se crea
        println(incidencias)

        //button onclicklistneer

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}