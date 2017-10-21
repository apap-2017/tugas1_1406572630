package com.example.model;

import com.example.model.PendudukModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private String id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private int jenis_kelamin;
	private int is_wni;
	private int id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private int is_wafat;
	private String nomor_kk;
	private KeluargaModel keluarga;
	private String RT;
	private String RW;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private String nkk;
	private String alamat;
	
	public String convertJenisKelamin() {
		if (jenis_kelamin == 0) {
			return "Pria";
		} else {
			return "Wanita";
		}
	}
	
	public String convertWNIStatus() {
		if (is_wni == 0) {
			return "WNA";
		} else {
			return "WNI";
		}
	}
	
	public String convertWafatStatus() {
		if (is_wafat == 1) {
			return "Meninggal";
		} else {
			return "Hidup";
		}
	}
}