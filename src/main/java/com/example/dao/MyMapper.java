package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface MyMapper {
	//nampilin penduduk
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = { @Result(property = "nik", column = "nik"), @Result(property = "nama", column = "nama"),
			@Result(property = "tempat_lahir", column = "tempat_lahir"),
			@Result(property = "tanggal_lahir", column = "tanggal_lahir"),
			@Result(property = "golongan_darah", column = "golongan_darah"),
			@Result(property = "agama", column = "agama"),
			@Result(property = "status_perkawinan", column = "status_perkawinan"),
			@Result(property = "pekerjaan", column = "pekerjaan"), @Result(property = "is_wni", column = "is_wni"),
			@Result(property = "is_wafat", column = "is_wafat"),

	})
	PendudukModel selectPenduduk(@Param("nik") String nik);
	
	//nampilin keluarga
	@Select("select * from penduduk where id_keluarga = #{id_keluarga}")
	@Results(value = { @Result(property = "nik", column = "nik"), @Result(property = "nama", column = "nama"),
			@Result(property = "tempat_lahir", column = "tempat_lahir"),
			@Result(property = "tanggal_lahir", column = "tanggal_lahir"),
			@Result(property = "golongan_darah", column = "golongan_darah"),
			@Result(property = "agama", column = "agama"),
			@Result(property = "status_perkawinan", column = "status_perkawinan"),
			@Result(property = "pekerjaan", column = "pekerjaan"), @Result(property = "is_wni", column = "is_wni"),
			@Result(property = "is_wafat", column = "is_wafat"),

	})
	List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);
	
	
	@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
	KeluargaModel selectKeluargabyNKK(String nomor_kk);

	@Select("select * from keluarga where id = #{id_keluarga}")
	KeluargaModel selectKeluargabyID(int id_keluarga);

	@Select("select * from kecamatan where id = #{id_kecamatan}")
	KecamatanModel selectKecamatanbyID(int id_kecamatan);

	@Select("select * from kelurahan where id = #{id_kelurahan}")
	KelurahanModel selectKelurahanbyID(int id_kelurahan);

	@Select("select kel.nama_kelurahan, kec.nama_kecamatan, kot.nama_kota "
			+ "FROM (SELECT id, nama_kota FROM kota) AS kot JOIN "
			+ "(SELECT id, id_kota, nama_kecamatan FROM kecamatan) AS kec " + "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id, id_kecamatan, nama_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan);

	@Select("select kel.kode_kelurahan, kec.kode_kecamatan, kot.kode_kota "
			+ "FROM (SELECT id, kode_kota FROM kota) AS kot JOIN "
			+ "(SELECT id, id_kota, kode_kecamatan FROM kecamatan) AS kec " + "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id_kecamatan, kode_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	AlamatModel getKodeAlamatbyIdKel(@Param("id_kelurahan") int id_kelurahan);

	@Select("select kec.kode_kecamatan AS kode_kecamatan, kel.kode_kelurahan AS kode_kelurahan "
			+ "FROM (SELECT id FROM kota WHERE id = #{id_kota}) AS kot JOIN "
			+ "(SELECT id, id_kota, kode_kecamatan FROM kecamatan WHERE id = #{id_kecamatan}) AS kec "
			+ "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id_kecamatan, kode_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	AlamatModel getKodeAlamatbyId(@Param("id_kelurahan") int id_kelurahan, @Param("id_kecamatan") int id_kecamatan,
			@Param("id_kota") int id_kota);

	@Insert("INSERT INTO penduduk(id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);

	@Insert("insert into keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "values ('${nomor_kk}', '${alamat}', '${RT}', '${RW}', '${id_kelurahan}', '0')")
	void addKeluarga(KeluargaModel keluarga);

	@Select("select id from kelurahan where kode_kelurahan = #{kode_kelurahan}")
	int getIdbyKodeKelurahan(String kode_kelurahan);

	@Select("select kel.id " + "FROM (SELECT id FROM kota WHERE nama_kota = #{nama_kota}) AS kot JOIN "
			+ "(SELECT id, id_kota FROM kecamatan WHERE nama_kecamatan = #{nama_kecamatan}) AS kec "
			+ "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id, id_kecamatan FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	Integer getIdKelurahanByAlamat(@Param("nama_kelurahan") String nama_kelurahan,
			@Param("nama_kecamatan") String nama_kecamatan, @Param("nama_kota") String nama_kota);

	@Select("select nik, nama, jenis_kelamin from penduduk JOIN "
			+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
			+ "ON keluarga.id = penduduk.id_keluarga")
	List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);

	@Select("select nik, nama, tanggal_lahir from penduduk JOIN "
			+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
			+ "ON keluarga.id = penduduk.id_keluarga " + "ORDER BY tanggal_lahir DESC " + "LIMIT 1")
	PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan);

	@Select("select id_kelurahan from keluarga where id = #{id_kelurahan}")
	String selectPendudukID(int id_kelurahan);

	@Select("select kode_kecamatan from kecamatan ke, kelurahan kel where kel.id = #{id_kelurahan} AND "
			+ "ke.id = kel.id_kecamatan")
	String selectKodeKecamatan(int id_kelurahan);

	@Update("UPDATE penduduk SET nik = '${penduduk.nik}', nama = '${penduduk.nama}', tempat_lahir = '${penduduk.tempat_lahir}', "
    		+ "tanggal_lahir = '${penduduk.tanggal_lahir}', jenis_kelamin = '${penduduk.jenis_kelamin}', "
    		+ "is_wni = '${penduduk.is_wni}', id_keluarga = '${penduduk.id_keluarga}', agama = '${penduduk.agama}', "
    		+ "pekerjaan = '${penduduk.pekerjaan}', status_perkawinan = '${penduduk.status_perkawinan}', "
    		+ "status_dalam_keluarga = '${penduduk.status_dalam_keluarga}', "
    		+ "golongan_darah = '${penduduk.golongan_darah}' "
    		+ "WHERE id = #{id}")
    void updatePenduduk(@Param("penduduk")PendudukModel penduduk, @Param("id")int id);

	@Select("select MAX(nik) from penduduk WHERE nik LIKE CONCAT(#{digitnik},'%')")
	String getNIKSebelum(String digitnik);

	@Select("select MAX(nomor_kk) from keluarga WHERE nomor_kk LIKE CONCAT(#{digitnkk},'%')")
	String getNKKSebelum(String digitnkk);

	@Select("select id, nama_kecamatan from kecamatan where kecamatan.id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatanList(@Param("id_kota") int id_kota);

	@Select("select id, nama_kecamatan from kecamatan")
	List<KecamatanModel> selectAllKecamatanList();

	@Select("select id, nama_kelurahan from kelurahan where kelurahan.id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanList(@Param("id_kecamatan") int id_kecamatan);

	@Select("select id, nama_kelurahan from kelurahan")
	List<KelurahanModel> selectAllKelurahanList();

	@Select("select * from kota where id = #{id_kota}")
	KotaModel selectKotabyID(int id_kota);

	@Select("select id, nama_kota from kota")
	List<KotaModel> selectKotaList();
	
	@Select("select * from penduduk where id = #{id}")
    PendudukModel selectPendudukbyId (String id);
	
	@Update("UPDATE penduduk SET  is_wafat = '1' WHERE nik = #{nik}")
    void updateStatusKematian(@Param("nik") String nik);
	
	@Update("update keluarga SET is_tidak_berlaku = '1' WHERE id = #{id}")
	void updateStatusBerlaku(@Param("id") int id);
	
	


}
