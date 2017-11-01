/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NoteDB;
import dataaccess.NotesDBException;
import domainmodel.Note;
import java.util.Date;
import java.util.List;


public class NoteService {
    private NoteDB noteDB;
    
    public Note get(int noteId) throws NotesDBException {
        noteDB = new NoteDB();
        return noteDB.getNote(noteId);
    }
    
    public List<Note> getAll() throws NotesDBException {
        noteDB = new NoteDB();
        return noteDB.getAll();
    }
    
    public int update(int noteId, Date date, String contents) throws NotesDBException {
        noteDB = new NoteDB();
        return noteDB.update(new Note(noteId, date, contents));
    }
    
    public int delete(Note note) throws NotesDBException {
        noteDB = new NoteDB();
        return noteDB.delete(note);
    }
    
    public int insert(String contents) throws NotesDBException {
        noteDB = new NoteDB();
        return noteDB.insert(new Note(0, new Date(), contents));
    }
}
