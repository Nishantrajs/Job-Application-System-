package com.embarkx.jobapp.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController
{
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies()
    {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/companies/{Id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long Id, @RequestBody Company company)
    {
        boolean bIsUpdated = companyService.updateCompany(Id,company);

        if(bIsUpdated)
            return new ResponseEntity<String>("COMPANY UPDATES SUCCESSFULLY !!!",HttpStatus.OK);

        return new ResponseEntity<String>("PROVIDED COMPANY DOESN'T EXISTS !! PLS CHECK AGAIN !",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/companies")
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        try {
            companyService.createCompany(company);
            return new ResponseEntity<>("Company added successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return new ResponseEntity<>("Error occurred while adding the Company !: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/companies/{Id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long Id)
    {
        boolean bIsDeleted = companyService.deleteCompany(Id);

        if(bIsDeleted)
            return new ResponseEntity<String>("COMPANY DELETED SUCCESSFULLY !!", HttpStatus.OK);

        return new ResponseEntity<String>("COMPANY NOT FOUND !! PLS CHECK !!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/companies/{Id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long Id)
    {
        Company company = companyService.getCompanyById(Id);

        if(company!=null)
            return new ResponseEntity<Company>(company, HttpStatus.OK);

        return new ResponseEntity<Company>(company, HttpStatus.NOT_FOUND);
    }
}
