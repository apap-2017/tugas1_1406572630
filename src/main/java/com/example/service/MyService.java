package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface MyService {


	PendudukModel selectPenduduk(String nik);
	
	List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);

	KeluargaModel selectKeluargabyNKK(String nomor_kk);

	KeluargaModel selectKeluargabyID(int id_keluarga);
	
	AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan);
	
	PendudukModel addPenduduk(PendudukModel penduduk);

	String selectPendudukID(int i);

	String selectKodeKecamatan(int id_kelurahan);

	KeluargaModel selectKeluargaById(Integer id);

	List<KecamatanModel> selectKecamatanList(int id_kota);

	List<KecamatanModel> selectKecamatanList();

	List<KelurahanModel> selectKelurahanList(int id_kecamatan);

	List<KelurahanModel> selectKelurahanList();

	KotaModel selectKotabyID(int id_kota);

	List<KotaModel> selectKotaList();

	String addKeluarga(KeluargaModel keluarga);

	void updateStatusKematian(PendudukModel penduduk);

	KecamatanModel selectKecamatanbyID(int id_kecamatan);

	KelurahanModel selectKelurahanbyID(int id_kelurahan);

	List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);

	PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan);

	PendudukModel getPendudukTertuaSekelurahan(int id_kelurahan);


}
