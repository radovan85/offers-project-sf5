package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TempConverter tempConverter;

	@Override
	public List<RoleDto> listAllAuthorities() {
		// TODO Auto-generated method stub
		List<RoleEntity> allRoles = roleRepository.findAll();
		List<RoleDto> returnValue = new ArrayList<>();
		
		for(RoleEntity roleEntity:allRoles){
			RoleDto roleDto = tempConverter.roleEntityToDto(roleEntity);
			returnValue.add(roleDto);
		}
		return returnValue;
	}

	@Override
	public RoleDto getAuthorityById(Integer id) {
		// TODO Auto-generated method stub
		RoleEntity roleEntity = roleRepository.getById(id);
		RoleDto returnValue = tempConverter.roleEntityToDto(roleEntity);
		return returnValue;
	}

}
