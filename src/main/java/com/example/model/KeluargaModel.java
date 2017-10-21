package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomor_kk;
	private String alamat;
	private String RT;
	private String RW;
	private int id_kelurahan;
	private int id_kecamatan;
	private int id_kota;
	private int is_tidak_berlaku;
	private boolean is_tidakberlaku;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private List<PendudukModel> anggota;
	
}
