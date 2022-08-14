package com.verifone.test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/record", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RecordRestController {

	@Autowired
	private RecordRepositiory repository;

	@GetMapping(value = "/")
	@ResponseStatus(code = HttpStatus.OK)
	public String okResponse() {
		return "OK";
	}

	@PostMapping(value = "/add")
	public Record addRecord(@RequestBody Record record) {
		return repository.save(record);
	}

	@GetMapping(value = "/listAll")
	public List<Record> getAllRecords() {
		return repository.findAll();
	}

	@PutMapping("/{id}")
	Record updateRecord(@RequestBody Record newRecord, @PathVariable Long id) {

		return repository.findById(id).map(record -> {
			record.setExpiryDate(record.getExpiryDate());
			record.setFullname(record.getFullname());
			record.setKyc(newRecord.isKyc());
			record.setMobileNumber(newRecord.getMobileNumber());
			record.setStateOFRegistration(newRecord.getStateOFRegistration());
			record.setTelecomProvider(record.getTelecomProvider());
			return repository.save(record);
		}).orElseGet(() -> {
			return repository.save(newRecord);
		});
		//		return newRecord;
	}

	@DeleteMapping("/{id}")
	void deleteRecord(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/to-renew")
	public List<Record> getAllRecordsExpiringInAMonth() {
		return repository.findAll().stream()
				.filter(record -> record.getExpiryDate().isBefore(LocalDateTime.now().plusMonths(1)))
				.collect(Collectors.toList());
	}

	@PutMapping("/renew/{id}")
	void renewRecord(@PathVariable Long id) {

		repository.findById(id).map(record -> {
			record.setExpiryDate(record.getExpiryDate().plusYears(1L));
			return repository.save(record);
		});
	}

}
