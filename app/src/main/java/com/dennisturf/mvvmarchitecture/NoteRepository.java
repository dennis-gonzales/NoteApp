package com.dennisturf.mvvmarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dennisturf.mvvmarchitecture.model.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    protected NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new insertNoteAyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new updateNoteAyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new deleteNoteAyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new deleteAllNoteAyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class insertNoteAyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private insertNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class updateNoteAyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private updateNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class deleteNoteAyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private deleteNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNoteAyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private deleteAllNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
