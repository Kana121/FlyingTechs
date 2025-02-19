package com.flyingtechs.userManagement.controller;

import com.flyingtechs.userManagement.dto.IdentityDocumentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "IdentityDocument API")
public interface IdentityDocumentController {
    @ApiOperation("Add new data")
    public IdentityDocumentDTO save(@RequestBody IdentityDocumentDTO identityDocument);

    @ApiOperation("Find by Id")
    public IdentityDocumentDTO findById(@PathVariable("id") Long id);

    @ApiOperation("Delete based on primary key")
    public void delete(@PathVariable("id") Long id);

    @ApiOperation("Find all data")
    public List<IdentityDocumentDTO> list();

    @ApiOperation("Pagination request")
    public Page<IdentityDocumentDTO> pageQuery(Pageable pageable);

    @ApiOperation("Update one data")
    public IdentityDocumentDTO update(@RequestBody IdentityDocumentDTO dto, @PathVariable("id") Long id);
}