package com.example.notesapplication.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.data.dao.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteDao: NoteDao): ViewModel() {

    val notes = noteDao.getAllNotes()
    val notesChannel = Channel<NotesEvent>()
    val notesEvent = notesChannel.receiveAsFlow()

    fun insertNote(note: com.example.notesapplication.data.entity.Note) = viewModelScope.launch {
        noteDao.insertNote(note)
        notesChannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun updateNote(note: com.example.notesapplication.data.entity.Note) = viewModelScope.launch {
        noteDao.updateNote(note)
        notesChannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun deleteNote(note: com.example.notesapplication.data.entity.Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
        notesChannel.send(NotesEvent.ShowUndoSnackBar("Note deleted successfully", note))
    }

    sealed class NotesEvent{
        data class ShowUndoSnackBar(val msg: String, val note: com.example.notesapplication.data.entity.Note): NotesEvent()
        object NavigateToNotesFragment: NotesEvent()
    }
}