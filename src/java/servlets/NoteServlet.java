/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.NoteService;
import dataaccess.NotesDBException;
import domainmodel.Note;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NoteService service = new NoteService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            String selectedNoteId = request.getParameter("selectedNoteId");
            try {
                Note note = service.get(Integer.parseInt(selectedNoteId));
                request.setAttribute("selectedNote", note);
            } catch (Exception ex) {
                Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            List<Note> noteList = service.getAll();
            request.setAttribute("noteTable", noteList);
            
        } catch (NotesDBException ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String noteId = request.getParameter("noteId");
        String contents = request.getParameter("contents");
        String action = request.getParameter("action");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        String dateString = request.getParameter("dateCreated");
        java.sql.Date date = null;
        try {
            if (dateString != null)
            {
                date = new java.sql.Date(dateFormat.parse(dateString).getTime());
            }
        } catch (ParseException ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        NoteService us = new NoteService();

        try {
            if (action.equals("delete")) {
                String selectedNoteId = request.getParameter("selectedNoteId");
                us.delete(us.get(Integer.parseInt(selectedNoteId)));
            } else if (action.equals("edit")) {
                Note note = new Note(Integer.parseInt(noteId), date, contents);
                us.update(note.getNoteId(), note.getDateCreated(), note.getContents());
            } else if (action.equals("add")) {
                us.insert(contents);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Whoops.  Could not perform that action.");
        }
        
        List<Note> notes = null;
        try {
            notes = (List<Note>) us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("noteTable", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
