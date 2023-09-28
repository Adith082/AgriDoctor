package com.visionaryproviders.agridoctor;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.visionaryproviders.agridoctor.repositories.RoleRepository;
import com.visionaryproviders.agridoctor.config.AppConstants;
import com.visionaryproviders.agridoctor.entities.Role;
@SpringBootApplication
public class AgridoctorAppApisApplication  implements CommandLineRunner{

	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AgridoctorAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		try {

			Role roleAdmin = new Role();
			roleAdmin.setId(AppConstants.ADMIN_USER);
			roleAdmin.setName("ROLE_ADMIN");

			Role roleNormal = new Role();
			roleNormal.setId(AppConstants.NORMAL_USER);
			roleNormal.setName("ROLE_NORMAL");

			List<Role> roles = List.of(roleAdmin, roleNormal);

			List<Role> result = this.roleRepository.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
	}

}
