package com.Teknisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Teknisi.services.TeknisiService;

@SpringBootApplication
public class SpringBootTeknisiApplication implements CommandLineRunner {
	
	@Autowired
	TeknisiService teknisiService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTeknisiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		Date teknisi_1_last_login = new Date();
//		Date teknisi_1_created_date = new Date();
//		Date teknisi_1_update_date = new Date();
		
		//Hard Code Teknisi n
//		Teknisi teknisi_1 = new Teknisi();
//		teknisi_1.setId(2L);
//		teknisi_1.setPhone("081294749377");
//		teknisi_1.setName("Tri");
//		teknisi_1.setNik("1999071900");
//		teknisi_1.setAddress("Jatiasih");
//		teknisi_1.setEmail("heliatgw@gmail.com");
//		teknisi_1.setCity("Bekasi");
//		teknisi_1.setPostal_code("17426");
//		teknisi_1.setLast_login(teknisi_1_last_login);
//		teknisi_1.setLongitude("150000");
//		teknisi_1.setLatitude("12300000");
//		teknisi_1.setCreated_date(teknisi_1_created_date);
//		teknisi_1.setCreated_by("Daniel");
//		teknisi_1.setUpdate_date(teknisi_1_update_date);
//		teknisi_1.setUpdate_by("Najoan");
		
		//System.out.println("Insert Data in Table");
//		teknisiService.insert(teknisi_1);
		 
		//System.out.println("Show All Data in Table");
//		teknisiService.showAllTeknisi();

		//System.out.println("Search By ID");
//		teknisiService.getTeknisiById(Long.valueOf(1L));
		
		//System.out.println("Update Data in Table");
//		teknisiService.updateTeknisi(teknisi_1);
		
		//System.out.println("Delete By ID");
//		teknisiService.deleteById(3L);
		
	}

}

//http://localhost:8181/swagger-ui.html#/
