package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.MyService;

@Controller
public class TheController {
	@Autowired
	MyService myService;

	////////////////// index///////////////////////

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/index")
	public String index2() {
		return "index";
	}

	/////////////////// tampilan//////////////////

	// tampilan penduduk
	@RequestMapping(value = "/penduduk", method = RequestMethod.GET)
	public String viewPenduduk(Model model, @RequestParam(value = "nik", required = true) String nik) {

		PendudukModel penduduk = myService.selectPenduduk(nik);
		KeluargaModel keluarga = myService.selectKeluargabyID(penduduk.getId_keluarga());
		System.out.println(keluarga);
		AlamatModel alamat = myService.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());

		if (penduduk == null) {
			model.addAttribute("nik", nik);
			return "not-found";
		} else {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("alamat", alamat);
			return "view-penduduk";
		}
	}

	// tampilan keluarga
	@RequestMapping(value = "/keluarga", method = RequestMethod.GET)
	public String viewKeluarga(Model model, @RequestParam(value = "nkk", required = true) String nomor_kk) {
		System.out.println("sebelum model" + nomor_kk);
		KeluargaModel keluarga = myService.selectKeluargabyNKK(nomor_kk);
		AlamatModel alamat = myService.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
		List<PendudukModel> anggota_keluarga = myService.selectAnggotaKeluarga(keluarga.getId());
		System.out.println("setelah model" + nomor_kk);
		System.out.println(alamat);
		System.out.println(anggota_keluarga);
		if (keluarga == null) {
			model.addAttribute("nkk", nomor_kk);
			return "not-found";
		} else {
			model.addAttribute("keluarga", keluarga);
			System.out.println("menuju" + nomor_kk);
			model.addAttribute("anggota_keluarga", anggota_keluarga);
			model.addAttribute("alamat", alamat);
			return "view-keluarga";

		}
	}

	/////////////// tambah//////////////

	// tambah penduduk
	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk(Model model) {
		model.addAttribute("penduduk", new PendudukModel());
		return "form-tambah-penduduk";
	}

	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String tambahPendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk) {
		PendudukModel new_penduduk = myService.addPenduduk(penduduk);
		System.out.println("masuk service");
		if (new_penduduk != null) {
			model.addAttribute("nik_tambah", new_penduduk.getNik());
			return "success-add-penduduk";
		} else {
			model.addAttribute("id_keluarga", penduduk.getId_keluarga());
			return "not-found";
		}
	}

	// tambah keluarga
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		List<KotaModel> kota_list = myService.selectKotaList();
		List<KecamatanModel> kecamatan_list = myService.selectKecamatanList();
		List<KelurahanModel> kelurahan_list = myService.selectKelurahanList();
		model.addAttribute("keluarga", new KeluargaModel());
		model.addAttribute("kota_list", kota_list);
		model.addAttribute("kecamatan_list", kecamatan_list);
		model.addAttribute("kelurahan_list", kelurahan_list);
		return "form-tambah-keluarga";
	}

	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String tambahKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga) {
		String nkk = myService.addKeluarga(keluarga);
		if (!nkk.equals("not ok")) {
			model.addAttribute("nkk_tambah", nkk);
			return "success-add-keluarga";
		} else {
			model.addAttribute("alamat", keluarga.getAlamat());
			return "not-found-tambah-keluarga";
		}
	}

	//////////// ubah////////////

	// ubah penduduk
	@RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = myService.selectPenduduk(nik);
		System.out.println(penduduk);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "form-ubah-penduduk";
		} else {
			model.addAttribute("nik_salah", nik);
			return "not-found";
		}
	}

	// ubah status kematian
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String ubahStatusKematianSubmit(Model model, @RequestParam(value = "nik", required = true) String nik) {
		PendudukModel penduduk = myService.selectPenduduk(nik);
		myService.updateStatusKematian(penduduk);

		model.addAttribute("nik_kematian", nik);
		return "success-ubah-status-kematian";
	}

	// //cari penduduk kota
	// @RequestMapping(value = "/penduduk/cari", method = RequestMethod.GET)
	// public String cariPendudukKota (Model model,
	// @RequestParam(value = "kt", required = false, defaultValue = "0") int
	// id_kota){
	// List<KotaModel> kota_list = myService.selectKotaList();
	// //List<KecamatanModel> kecamatan_list = myService.selectKecamatanList();
	// model.addAttribute("kota_list", kota_list);
	// model.addAttribute("kecamatan_list",
	// myService.selectKecamatanList(id_kota));
	// //model.addAttribute("kecamatan_list", kecamatan_list);
	// System.out.println(kota_list);
	// System.out.println(myService.selectKecamatanList(id_kota));
	// return "cari-penduduk-kota";
	// }
	//
	// //cari penduduk kecamatan
	// @RequestMapping(value = "/penduduk/cari?kt={id_kota}&kc={id_kecamatan}",
	// method = RequestMethod.GET)
	// public String cariPendudukKecamatan (Model model,
	// @RequestParam(value = "kt", required = false, defaultValue = "0") int
	// id_kota,
	// @RequestParam(value = "kc", required = false, defaultValue = "0") int
	// id_kecamatan){
	// List<KotaModel> kota_list = myService.selectKotaList();
	//
	// model.addAttribute("kota_list", kota_list);
	// model.addAttribute("kecamatan_list",
	// myService.selectKecamatanList(id_kota));
	// model.addAttribute("kelurahan_list",
	// myService.selectKelurahanList(id_kecamatan));
	//
	// System.out.println(kota_list);
	// System.out.println(myService.selectKecamatanList(id_kota));
	// System.out.println(myService.selectKelurahanList(id_kecamatan));
	// return "cari-penduduk-kecamatan";
	// }

	// cari penduduk
	@RequestMapping(value = "/penduduk/cari")
	public String cariPenduduk(Model model,
			@RequestParam(value = "kt", required = false, defaultValue = "0") int id_kota,
			@RequestParam(value = "kc", required = false, defaultValue = "0") int id_kecamatan,
			@RequestParam(value = "kl", required = false, defaultValue = "0") int id_kelurahan) {
		List<KotaModel> kota_list = myService.selectKotaList();
		model.addAttribute("kota_list", kota_list);
		System.out.println(kota_list);
		if (id_kelurahan != 0) {
			model.addAttribute("nama_kota", myService.selectKotabyID(id_kota).getNama_kota());
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("nama_kecamatan", myService.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			model.addAttribute("id_kecamatan", id_kecamatan);
			model.addAttribute("nama_kelurahan", myService.selectKelurahanbyID(id_kelurahan).getNama_kelurahan());
			model.addAttribute("id_kelurahan", id_kelurahan);
			model.addAttribute("view", "view");
			model.addAttribute("penduduk_list", myService.selectPendudukByIdKelurahan(id_kelurahan));
			model.addAttribute("penduduk_termuda", myService.getPendudukTermuda(id_kelurahan));
			model.addAttribute("penduduk_tertua", myService.getPendudukTertua(id_kelurahan));
		} else if (id_kecamatan != 0) {
			model.addAttribute("nama_kota", myService.selectKotabyID(id_kota).getNama_kota());
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("nama_kecamatan", myService.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			model.addAttribute("id_kecamatan", id_kecamatan);
			model.addAttribute("kelurahan_list", myService.selectKelurahanList(id_kecamatan));
		} else if (id_kota != 0) {
			model.addAttribute("nama_kota", myService.selectKotabyID(id_kota).getNama_kota());
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("kecamatan_list", myService.selectKecamatanList(id_kota));
		}
		return "cari-penduduk";
	}
}
