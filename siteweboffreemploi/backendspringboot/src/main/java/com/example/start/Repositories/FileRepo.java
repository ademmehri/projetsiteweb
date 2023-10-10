package com.example.start.Repositories;

import com.example.start.Entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File,Long> {
	 @Query(value = "select * from employee e , file f where e.id=f.emp_id and e.mail = :mail and f.nomfichier ='image' ", nativeQuery = true)
	    public File getfile(@Param("mail") String mail);
	    @Query(value = "select * from employee e , file f where e.id=f.emp_id and f.nomfichier ='image'  and e.id = :id and e.role = :role", nativeQuery = true)
	    public File updatefile(@Param("id") Long id,@Param("role") String role);
	    @Query(value = "select * from employee e , file f where e.id=f.emp_id and f.nomfichier ='fichier'  and e.id = :id and e.role = :role", nativeQuery = true)
	    public File updatecv(@Param("id") Long id,@Param("role") String role);
	    @Query(value = "select * from employee e , file f where e.id=f.emp_id and f.nomfichier ='image' and e.id = :cin", nativeQuery = true)
	    public File getfileemp(@Param("cin") Long id);
	    @Query(value = "select * from employee e , file f where e.id=f.emp_id and f.nomfichier ='fichier' and e.id = :cin", nativeQuery = true)
	    public File getcv(@Param("cin") Long id);
	
}
