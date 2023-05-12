package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.ParticipantEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.OfferRepository;
import com.radovan.spring.repository.ParticipantRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.UserRepository;
import com.radovan.spring.service.UserService;

@Service("userServiceHandler")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TempConverter tempConverter;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private OfferRepository offerRepository;

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
		userRepository.flush();
	}

	@Override
	public UserEntity getUserById(Integer id) {
		// TODO Auto-generated method stub
		UserEntity returnValue = userRepository.getById(id);
		return returnValue;
	}

	@Override
	public List<UserDto> listAllUsers() {
		// TODO Auto-generated method stub
		List<UserEntity> allUsers = userRepository.findAll();
		List<UserDto> returnValue = new ArrayList<UserDto>();
		
		for(UserEntity userEntity:allUsers) {
			UserDto userDto = tempConverter.userEntityToDto(userEntity);
			returnValue.add(userDto);
		}
		return returnValue;
	}

	@Override
	public UserDto getUser(Integer id) {
		// TODO Auto-generated method stub
		UserEntity userEntity = getUserById(id);
		UserDto returnValue = tempConverter.userEntityToDto(userEntity);
		return returnValue;
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		// TODO Auto-generated method stub
		UserEntity returnValue = userRepository.findByEmail(email);
		
		return returnValue;
	}

	@Override
	public UserDto addUser(UserDto user) {
		// TODO Auto-generated method stub
		RoleEntity role = roleRepository.findByRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled((byte) 1);
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(role);
		UserEntity userEntity = mapper.map(user,UserEntity.class);
		userEntity.setRoles(roles);
		UserEntity storedUser = userRepository.save(userEntity);
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(storedUser);
		role.setUsers(users);
		roleRepository.saveAndFlush(role);
		
		ParticipantDto participantDto = new ParticipantDto();
		participantDto.setUserId(storedUser.getId());
		
		ParticipantEntity participantEntity = mapper.map(participantDto, ParticipantEntity.class);
		participantEntity.setUser(storedUser);
		participantEntity.setFullName(storedUser.getFirstName() + " " + storedUser.getLastName());
		participantRepository.saveAndFlush(participantEntity);
		
		
		UserEntity updatedUser = userRepository.saveAndFlush(storedUser);
		UserDto returnValue = tempConverter.userEntityToDto(updatedUser);
		
		
		return returnValue;
	}

	@Override
	public List<UserEntity> listAll() {
		// TODO Auto-generated method stub
		List<UserEntity> returnValue = userRepository.findAll();
		return returnValue;
	}
	
	@Override
	public UserDto getCurrentUser() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto returnValue = tempConverter.userEntityToDto(authUser);
		return returnValue;
	}

	@Override
	public void suspendUser(Integer participantId) {
		// TODO Auto-generated method stub
		ParticipantEntity participantEntity = participantRepository.getById(participantId);
		offerRepository.deleteAllByParticipantId(participantId);
		offerRepository.flush();
		
		Integer userId = participantEntity.getUser().getId();
		UserEntity authUser = userRepository.getById(userId);
		authUser.setEnabled((byte) 0);
		
	}

	

}
