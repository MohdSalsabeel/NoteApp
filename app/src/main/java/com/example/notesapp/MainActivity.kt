package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val recyclerView = this.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this,
         ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list->
            list?.let{
                adapter.updateList(it)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.text} Deleted",Toast.LENGTH_SHORT).show()
    }

    fun submitData(view: android.view.View) {

        val noteText = input.text.toString()
        if(noteText.isNotEmpty()){
            Toast.makeText(this,"${noteText} inserted",Toast.LENGTH_SHORT).show()
            viewModel.insertNote(Note(noteText))
        }
    }


}