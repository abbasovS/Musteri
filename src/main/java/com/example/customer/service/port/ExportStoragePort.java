package com.example.customer.service.port;


public interface ExportStoragePort {

    String uploadCustomersExport(byte[] fileContent, String filename, String contentType);

    String generateSignedUrl(String storedFileUrl);
}