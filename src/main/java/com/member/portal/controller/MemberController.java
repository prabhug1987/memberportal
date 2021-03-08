package com.member.portal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.member.portal.exception.MemberNotFoundException;
import com.member.portal.model.Member;
import com.member.portal.repository.MemberRepository;

@RestController
public class MemberController {
	private final MemberRepository repository;

	public MemberController(MemberRepository repository) {
		this.repository = repository;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/members")
	List<Member> all() {
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/Members")
	public Member newMember(@RequestBody Member newMember) {
		return repository.save(newMember);
	}

	// Single item

	@GetMapping("/members/{id}")
	public Member one(@PathVariable Long id) {

		return repository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
	}

	@PutMapping("/Members/{id}")
	public Member replaceMember(@RequestBody Member newMember, @PathVariable Long id) {

		return repository.findById(id).map(Member -> {
			Member.setFirstName(newMember.getFirstName());
			Member.setLastName(newMember.getLastName());
			return repository.save(Member);
		}).orElseGet(() -> {
			// newMember.setId(id);
			return repository.save(newMember);
		});
	}

	@DeleteMapping("/Members/{id}")
	public void deleteMember(@PathVariable Long id) {
		repository.deleteById(id);
}
