package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MyMapper;
import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyServiceDatabase implements MyService {

	@Autowired
	private MyMapper myMapper;

	@Override
	public PendudukModel selectPenduduk(String nik) {
		System.out.println(nik);
		return myMapper.selectPenduduk(nik);
	}

	@Override
	public List<PendudukModel> selectAnggotaKeluarga(int id_keluarga) {

		return myMapper.selectAnggotaKeluarga(id_keluarga);
	}

	@Override
	public KeluargaModel selectKeluargabyNKK(String nomor_kk) {
		System.out.println("by nkk" + nomor_kk);
		return myMapper.selectKeluargabyNKK(nomor_kk);
	}

	@Override
	public KeluargaModel selectKeluargabyID(int id_keluarga) {
		System.out.println("by id" + id_keluarga);
		return myMapper.selectKeluargabyID(id_keluarga);
	}

	@Override
	public AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan) {
		return myMapper.getAlamatLengkapbyIdKel(id_kelurahan);
	}

	@Override
	public String selectPendudukID(int id_kelurahan) {
		log.info("", id_kelurahan);
		return myMapper.selectPendudukID(id_kelurahan);
	}

	@Override
	public KeluargaModel selectKeluargaById(Integer id) {
		log.info("select keluarga with id {}", id);
		return myMapper.selectKeluargabyID(id);
	}

	@Override
	public String selectKodeKecamatan(int id_kelurahan) {
		// TODO Auto-generated method stub
		return myMapper.selectKodeKecamatan(id_kelurahan);
	}

	@Override
	public PendudukModel addPenduduk(PendudukModel penduduk) {
		log.info("add penduduk with id keluarga {}", penduduk.getNik());

		String nik_penduduk = generateNik(penduduk);
		penduduk.setNik(nik_penduduk);
		myMapper.addPenduduk(penduduk);
		return penduduk;
	}

	public String generateNik(PendudukModel penduduk) {
		KeluargaModel keluarga = myMapper.selectKeluargabyID(penduduk.getId_keluarga());
		System.out.println(keluarga.getId());
		AlamatModel alamat = myMapper.getKodeAlamatbyIdKel(keluarga.getId_kelurahan());

		String tanggal_lahir = penduduk.getTanggal_lahir();
		String tanggal = tanggal_lahir.substring(8, 10);
		if (penduduk.getJenis_kelamin() == 1) {
			int tgl = Integer.parseInt(tanggal);
			tgl = tgl + 40;
			tanggal = Integer.toString(tgl);
		}

		String kelahiran = tanggal + tanggal_lahir.substring(5, 7) + tanggal_lahir.substring(2, 4);
		String kode_kecamatan = alamat.getKode_kecamatan();
		String digitnik = kode_kecamatan.substring(0, kode_kecamatan.length() - 1) + kelahiran;

		String nik_sebelum = myMapper.getNIKSebelum(digitnik);
		Long nik = Long.parseLong(digitnik + "0001");
		if (nik_sebelum != null) {
			nik = Long.parseLong(nik_sebelum) + 1;
		}

		String nik_penduduk = Long.toString(nik);
		return nik_penduduk;
	}

	

	///////////////// Keluarga//////////////////////

	@Override
	public String addKeluarga(KeluargaModel keluarga) {
		int id_kelurahan = keluarga.getId_kelurahan();
		int id_kecamatan = keluarga.getId_kecamatan();
		int id_kota = keluarga.getId_kota();
		System.out.println("kelurahan" + id_kelurahan + "kecamatan" + id_kecamatan + "kota" + id_kota);
		AlamatModel kode_alamat = myMapper.getKodeAlamatbyId(id_kelurahan, id_kecamatan, id_kota);
		System.out.println(kode_alamat);
		if (kode_alamat != null) {
			String kode_kecamatan = kode_alamat.getKode_kecamatan();
			String kode_kelurahan = kode_alamat.getKode_kelurahan();

			LocalDate localDate = LocalDate.now();
			String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);

			String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2, 4);
			String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length() - 1) + tanggal_rilis;

			String nkk_sebelum = myMapper.getNKKSebelum(digitnkk);
			Long nkk = Long.parseLong(digitnkk + "0001");
			if (nkk_sebelum != null) {
				nkk = Long.parseLong(nkk_sebelum) + 1;
			}

			String nkk_keluarga = Long.toString(nkk);
			keluarga.setNomor_kk(nkk_keluarga);
			keluarga.setId_kelurahan(myMapper.getIdbyKodeKelurahan(kode_kelurahan));
			myMapper.addKeluarga(keluarga);
			return nkk_keluarga;

		} else {
			return "not ok";
		}
	}

	@Override
	public List<KecamatanModel> selectKecamatanList(int id_kota) {
		return myMapper.selectKecamatanList(id_kota);
	}

	@Override
	public List<KecamatanModel> selectKecamatanList() {
		return myMapper.selectAllKecamatanList();
	}

	@Override
	public List<KelurahanModel> selectKelurahanList(int id_kecamatan) {
		return myMapper.selectKelurahanList(id_kecamatan);
	}

	@Override
	public List<KelurahanModel> selectKelurahanList() {
		return myMapper.selectAllKelurahanList();
	}

	@Override
	public KotaModel selectKotabyID(int id_kota) {
		log.info("select kota with id  kota ()", id_kota);
		return myMapper.selectKotabyID(id_kota);
	}

	@Override
	public List<KotaModel> selectKotaList() {
		return myMapper.selectKotaList();
	}
	
	@Override
	public void updateStatusKematian(PendudukModel penduduk) {
		int id_keluarga = penduduk.getId_keluarga();
		List<PendudukModel> anggota_keluarga = myMapper.selectAnggotaKeluarga(id_keluarga);
		int wafat = 0;
		for (PendudukModel p : anggota_keluarga) {
			if (p.getIs_wafat() == 1) {
				wafat = wafat + 1;
			}
		}
		 
		if (wafat == anggota_keluarga.size()) {
			 myMapper.updateStatusBerlaku(id_keluarga);
		}
		myMapper.updateStatusKematian(penduduk.getNik());
	}
	
	@Override
	public KecamatanModel selectKecamatanbyID(int id_kecamatan) {
    	log.info("select kecamatan with id  kecamatan ()", id_kecamatan);
    	return myMapper.selectKecamatanbyID(id_kecamatan);
    }
	
	@Override
	public KelurahanModel selectKelurahanbyID(int id_kelurahan) {
		log.info("select kelurahan with id  kelurahan ()", id_kelurahan);
		return myMapper.selectKelurahanbyID(id_kelurahan);
	}
	
	@Override
	public List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan){
		return myMapper.selectPendudukByIdKelurahan(id_kelurahan);
	}
	
	@Override
	public PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan) {
		return myMapper.getPendudukTermudaSekelurahan(id_kelurahan);
	}
	
	@Override
	public PendudukModel getPendudukTertuaSekelurahan(int id_kelurahan) {
		return myMapper.getPendudukTertuaSekelurahan(id_kelurahan);
	}
	
	
}
