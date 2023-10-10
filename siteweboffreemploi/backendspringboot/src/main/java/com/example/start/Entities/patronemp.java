package com.example.start.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class patronemp {
	private Long id;
	private String nom;
	private String dpe;
	private String region;
	private String city;
	private String num;
	private File filee;

}
