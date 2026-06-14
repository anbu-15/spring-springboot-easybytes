package com.eazybytes.jobportal.contact.service.impl;

import com.eazybytes.jobportal.constants.ApplicationConstants;
import com.eazybytes.jobportal.contact.service.IContactService;
import com.eazybytes.jobportal.dto.ContactRequestDto;
import com.eazybytes.jobportal.dto.ContactResponseDto;
import com.eazybytes.jobportal.entity.Contact;
import com.eazybytes.jobportal.repository.ContactRepository;
import com.eazybytes.jobportal.util.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Transactional
    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        boolean result = false;
        Contact contact = contactRepository.save(transformToEntity(contactRequestDto));
        if (contact != null && contact.getId() != null) result = true;
        return result;
    }

    @Override
    public List<ContactResponseDto> fetchOpenContactMsgs() {
        List<Contact> contacts = contactRepository.findContactsByStatusOrderByCreatedAtAsc(ApplicationConstants.NEW_MESSAGE);
        List<ContactResponseDto> contactResponseDtos = contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
        return contactResponseDtos;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus(
                ApplicationConstants.NEW_MESSAGE, sort
        );
        List<ContactResponseDto> responseDtos = contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
        return responseDtos;
    }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortBy.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Contact> contactPage = contactRepository.findContactsByStatus(
                ApplicationConstants.NEW_MESSAGE, pageable);

        Page<ContactResponseDto> responseDtoPage = contactPage
                .map(this::transformToDto);
        return responseDtoPage;
    }

    //    @Transactional(isolation = Isolation.DEFAULT)
//    @Transactional(timeout = 10)
//    @Transactional(propagation = Propagation.REQUIRED)//Default one is required there are 7 types of
//    propagation (REQUIRED,MANDATORY,SUPPORTS,NOT_SUPPORTED,REQUIRES_NEW,NEVER,NESTED)
    @Transactional//(rollbackFor = IOException.class,Exception.class)
//    @Transactional(rollbackForClassName = "Exception",noRollbackFor = SQLException.class)
    @Override
    public boolean closeContactMsg(Long id, String status) {
        //1) Update status
        //2) Insert in another table
        //3) To delete the record
        int updatedRows = contactRepository.updateStatusById(status, id, ApplicationUtility.getLoggedInUser());
/*          throw new NullPointerException("Its a bad day");
            throw new IOException("Its a bad day");
            It is run time exception so that the data in DB doesnt changed
            If it is a IOException the data will change in the DB so we need to use the
            (rollbackFor = Exception.class) in the annotation the data wont change in the DB
 */
        return updatedRows > 0;
    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto, contact);
//        contact.setCreatedAt(Instant.now());
//        contact.setUpdatedAt(Instant.now());
        contact.setStatus("NEW");
        return contact;
    }

    private ContactResponseDto transformToDto(Contact contact) {
        ContactResponseDto contactRequestDto = new ContactResponseDto(
                contact.getId(), contact.getName(), contact.getEmail(),
                contact.getUserType(), contact.getSubject(), contact.getMessage(),
                contact.getStatus(), contact.getCreatedAt()
        );
        return contactRequestDto;
    }
}
