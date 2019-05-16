package com.example.noteynotes.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.noteynotes.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDB;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDB= AppDatabase.getInstance(context);
        mNotes  = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes(){
        return mDB.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDao().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDB.noteDao().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDao().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDB.noteDao().deleteNote(note);
            }
        });
    }
}
