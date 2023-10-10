package com.example.start.Services;

import com.example.start.Entities.Employee;

import com.example.start.Entities.File;
import com.example.start.Repositories.EmployeeRepo;

import com.example.start.Repositories.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
@Service
public class FileService {
    @Autowired
    FileRepo fileemprep;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeService empservice;

  

    public File addfile(MultipartFile f) throws IOException {
		File fl=new File(f.getOriginalFilename(),f.getContentType(),compressBytes(f.getContentType().getBytes()));
		fl.setNomfichier("image");
		String path="C://Users//Lenovo//php//formation angular//testangular//projetangular//src//assets";
		Path pt=Paths.get(path+"/"+f.getOriginalFilename());
		Files.copy(f.getInputStream(), pt,StandardCopyOption.REPLACE_EXISTING);
		return fileemprep.save(fl);
    }
    public File addcv(MultipartFile f) throws IOException {
  		File fl=new File(f.getOriginalFilename(),f.getContentType(),compressBytes(f.getContentType().getBytes()));
  		fl.setNomfichier("fichier");
  		String path="C://Users//Lenovo//php//formation angular//testangular//projetangular//src//assets";
  		Path pt=Paths.get(path+"/"+f.getOriginalFilename());
  		Files.copy(f.getInputStream(), pt,StandardCopyOption.REPLACE_EXISTING);
  		return fileemprep.save(fl);
      }
    public File  getfileemployee(String mail) {
    	Employee e=employeeRepo.findBymail(mail);
    	File f=fileemprep.getfile(mail);
    	File fil = new File(f.getTitlefile(), f.getTypefile(),
				decompressBytes(f.getTaillefile()));
    
    return fil;
    	
    }
    public File  getfileemp(Long id) {
    	Employee e=employeeRepo.findById(id).get();
    	File f=fileemprep.getfileemp(id);
    	File fil = new File(f.getTitlefile(), f.getTypefile(),
				decompressBytes(f.getTaillefile()));
    
    return fil;
    	
    }
    public File  getcv(Long id) {
    	Employee e=employeeRepo.findById(id).get();
    	File f=fileemprep.getcv(id);
    	File fil = new File(f.getTitlefile(), f.getTypefile(),
				decompressBytes(f.getTaillefile()));
    
    return fil;
    	
    }
    public File updatefile(Long id ,MultipartFile file) throws IOException {
       	Employee e=employeeRepo.findById(id).get();
       	File fl= fileemprep.updatefile(id,e.getRole());
  
    	File f=new File(file.getOriginalFilename(),file.getContentType(),compressBytes(file.getContentType().getBytes()));;
    	String path="C://Users//Lenovo//php//formation angular//testangular//projetangular//src//assets";
		Path pt=Paths.get(path+"/"+file.getOriginalFilename());
		Files.copy(file.getInputStream(), pt,StandardCopyOption.REPLACE_EXISTING);
    	fl.setTaillefile(f.getTaillefile());
    	fl.setTitlefile(f.getTitlefile());
    	fl.setTypefile(f.getTypefile());
    	fl.setNomfichier("image");
    	return fileemprep.save(fl);

    }
    public File updatecv(Long id ,MultipartFile file) throws IOException {
       	Employee e=employeeRepo.findById(id).get();
    	File fl= fileemprep.updatecv(id,e.getRole());
    	if(fl!=null) {
    		File f=new File(file.getOriginalFilename(),file.getContentType(),compressBytes(file.getContentType().getBytes()));;
        	String path="C://Users//Lenovo//php//formation angular//testangular//projetangular//src//assets";
    		Path pt=Paths.get(path+"/"+file.getOriginalFilename());
    		Files.copy(file.getInputStream(), pt,StandardCopyOption.REPLACE_EXISTING);
    		fl.setTaillefile(f.getTaillefile());
        	fl.setTitlefile(f.getTitlefile());
        	fl.setTypefile(f.getTypefile());
        	fl.setNomfichier("fichier");
        	return fileemprep.save(fl);
    	}
    	 empservice.addcv(e.getMail(), file);
    	 return fl;
    }
 
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
    	Inflater inflater = new Inflater();
    	inflater.setInput(data);
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    	byte[] buffer = new byte[1024];
    	try {
    	while (!inflater.finished()) {
    	int count = inflater.inflate(buffer);
    	outputStream.write(buffer, 0, count);
    	}
    	outputStream.close();
    	} catch (IOException ioe) {
    	} catch (DataFormatException e) {
    	}
    	return outputStream.toByteArray();
    	}

}
