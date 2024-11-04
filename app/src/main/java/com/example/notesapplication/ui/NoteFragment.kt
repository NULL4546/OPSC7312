package com.example.notesapplication.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapplication.R
import com.example.notesapplication.adapter.NoteAdapter
import com.example.notesapplication.data.entity.Note
import com.example.notesapplication.databinding.FragmentNotesBinding
import com.example.notesapplication.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment: Fragment(R.layout.fragment_notes), NoteAdapter.OnNoteClickListener {
    val viewModel by viewModels<NoteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNotesBinding.bind(requireView())
        val navController = findNavController()

        binding.apply {
            recyclerViewNotes.layoutManager = GridLayoutManager(context, 2)
            recyclerViewNotes.setHasFixedSize(true)

            floatingActionButton2.setOnClickListener {
                val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(null)
                findNavController().navigate(action)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notes.collect { notes ->
                    val adapter = NoteAdapter(notes, this@NoteFragment)
                    recyclerViewNotes.adapter = adapter
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notesEvent.collect { event ->
                    if (event is NoteViewModel.NotesEvent.ShowUndoSnackBar) {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.insertNote(event.note)
                            }.show()
                    }
                }

            }

        }

    }
    override fun onNoteClick(note: Note) {
        val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onNoteLngClick(note: Note) {
        viewModel.deleteNote(note)
    }
}
