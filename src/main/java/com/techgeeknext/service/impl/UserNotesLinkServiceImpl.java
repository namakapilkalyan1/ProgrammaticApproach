package com.techgeeknext.service.impl;

import com.techgeeknext.modal.Notes;

import com.techgeeknext.modal.User;
import com.techgeeknext.service.NotesService;
import com.techgeeknext.service.UserNotesLinkService;
import com.techgeeknext.service.UserService;
import com.techgeeknext.util.ApplicationConstants;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class UserNotesLinkServiceImpl implements UserNotesLinkService {
	static int count = 0;
    @Autowired
    private UserService userService;

    @Autowired
    private NotesService notesService;
    
  @Autowired
    private PlatformTransactionManager transactionManager;
 
    
    @Override
    public String addNoteToSpecificUser(User user, Notes note) {
    	DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    TransactionStatus status = transactionManager.getTransaction(definition);
    try {
  	  User createdUser = userService.registerUser(user);
        Notes dbNote = new Notes();
        dbNote.setTitle(note.getTitle());
        dbNote.setMessage(note.getMessage());
        dbNote.setNoteId(note.getNoteId());
        //set created user to note
        dbNote.setUserDetails(createdUser);
        //persist new note
        notesService.addNote(dbNote);
         //if(note.getTitle()=="Testing1 Note")
         //{
         	//throw new Exception();
         //}
        transactionManager.commit(status);
         return ApplicationConstants.ADDED_NOTE_DESC;

    }
    catch (Exception ex) {
        transactionManager.rollback(status);
        return ApplicationConstants.ADDED_NOTE_DESC;
    }
    }  
}


          
