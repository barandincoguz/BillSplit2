import React, { useEffect, useState } from "react";
import "./sidebar.css";
import { HiMinusCircle } from "react-icons/hi";
import { HiOutlinePencil } from "react-icons/hi";
import axios from "axios";

const Sidebar = () => {
	const [ad, setAd] = useState("");
	const [soyad, setSoyad] = useState("");
	const [odedigiTutar, setOdedigiTutar] = useState(""); // Initialize as an empty string
	const [id, setId] = useState(null);
	const [personList, setPersonList] = useState([]);
	const [error, setError] = useState("");
	const [edittedPerson, setEdittedPerson] = useState(null);
	const regex = /^[A-Za-zçÇğĞıİöÖşŞüÜ ]+$/;



	const validateForm = (ad, soyad, odedigiTutar, regex, setError) => {
		setError("");
		if (String(odedigiTutar).trim() === "" || isNaN(odedigiTutar)) {
			setError("Lütfen ödediğiniz tutarı giriniz");
			return false;
		}
		if (!regex.test(ad.trim()) || !regex.test(soyad.trim())) {
			setError("Ad veya soyad sadece harf içerebilir.");
			return false;
		}
		return true;
	};

	const fetchPersonList = async () => {
		try {
			const response = await axios.get(
				"http://localhost:8080/api/person/list"
			);
			setPersonList(response.data);
		} catch (error) {
			console.error("Fetching failed", error);
		}
	};
	useEffect(() => {
			fetchPersonList();
		},
		[]);

	const handleRemove = async (id) => {
		try {
			const response = await axios.delete(
				`http://localhost:8080/api/person/delete/${id}`
			);
			if (response.status === 200) {
				// API başarılı yanıt verirse
				const updatedList = personList.filter(
					(person) => person.id !== id
				);
				setPersonList(updatedList);

				console.log("person  deleted id :  ", id);
			}
		} catch (error) {
			console.error("Error removing person:", error);
		}
	};

	const showEditForm = (person) => {
		const editForm = document.querySelector(".editForm");
		editForm.style.visibility = "visible";
		editForm.style.opacity = 1;
		setEdittedPerson(person);
		setId(person.id);
		setAd(person.ad);
		setSoyad(person.soyad);
		setOdedigiTutar(person.odedigiTutar);
	};

	const handleEdit = async (event) => {
		event.preventDefault();
		if (!validateForm(ad, soyad, odedigiTutar, regex, setError)) {
			return;
		}

		if (edittedPerson) {
			const updatedPerson = {
				id,
				ad,
				soyad,
				odedigiTutar: parseFloat(odedigiTutar).toFixed(2),
			};
			try {
				await axios.put(
					`http://localhost:8080/api/person/update/${id}`,
					updatedPerson
				);
				const updatedList = personList.map((person) =>
					person.id === id ? updatedPerson : person
				);
				setPersonList(updatedList);
				setEdittedPerson(null);
				const editForm = document.querySelector(".editForm");
				editForm.style.visibility = "hidden";
				editForm.style.opacity = 0;
				document.querySelector("form").reset();
				setAd("");
				setSoyad("");
				setOdedigiTutar(""); // Reset to empty string
			} catch (error) {
				console.error("Error updating person:", error);
			}
		}
	};

	const handleSubmit = async (event) => {
		event.preventDefault();
		if (!validateForm(ad, soyad, odedigiTutar, regex, setError)) {
			return;
		}

		if (!error) {
			const newPerson = {
				ad,
				soyad,
				odedigiTutar: parseFloat(odedigiTutar).toFixed(2),
			};
			try {
				const response = await axios.post(
					"http://localhost:8080/api/person/createPerson",
					newPerson
				);
				setPersonList([...personList, response.data]);
				console.log(personList);
				document.querySelector("form").reset();
				setAd("");
				setSoyad("");
				setOdedigiTutar(""); // Reset to empty string
			} catch (error) {
				console.error("Error adding person:", error);
			}
		}
	};

	return (
		<>
			<div className="editForm">
				<div className="mainForm">
					<form className="form" onSubmit={handleEdit}>
						<label htmlFor="Ad">Ad</label>
						<input
							type="text"
							name="Ad"
							id="Ad"
							required
							value={ad}
							onChange={(e) => setAd(e.target.value)}
						/>
						<label htmlFor="Soyad">Soyad</label>
						<input
							type="text"
							name="Soyad"
							id="Soyad"
							required
							value={soyad}
							onChange={(e) => setSoyad(e.target.value)}
						/>
						<label htmlFor="odedigiTutar">Ödediği Tutar</label>
						<input
							type="number"
							name="odedigiTutar"
							id="odedigiTutar"
							step="0.01"
							min="0"
							required
							value={odedigiTutar}
							onChange={(e) => setOdedigiTutar(e.target.value)}
						/>
						{error && <h4>{error}</h4>}
						<button type="submit" id="submitButton" className="buttons">
							GÜNCELLE
						</button>
					</form>
				</div>
			</div>

			<div className="sidebar">
				<div className="mainForm">
					<form className="form" onSubmit={handleSubmit}>
						<label htmlFor="Ad">Ad</label>
						<input
							type="text"
							name="Ad"
							id="Ad"
							required
							value={ad}
							onChange={(e) => setAd(e.target.value)}
						/>
						<label htmlFor="Soyad">Soyad</label>
						<input
							type="text"
							name="Soyad"
							id="Soyad"
							required
							value={soyad}
							onChange={(e) => setSoyad(e.target.value)}
						/>
						<label htmlFor="odedigiTutar">Ödediği Tutar</label>
						<input
							type="number"
							name="odedigiTutar"
							id="odedigiTutar"
							step="0.01"
							min="0"
							required
							value={odedigiTutar}
							onChange={(e) => setOdedigiTutar(e.target.value)}
						/>
						{error && <h4>{error}</h4>}
						<button type="submit" className="buttons">
							KİŞİ EKLE
						</button>
					</form>
				</div>

				<div className="listPersons">
					{personList.map((person) => (
						<div key={person.id} className="personCard">
							<div>
								<strong>Ad:</strong> {person.ad}
							</div>
							<div>
								<strong>Soyad:</strong> {person.soyad}
							</div>
							<div>
								<strong>Ödediği Tutar:</strong>{" "}
								{person.odedigiTutar}
							</div>
							<HiMinusCircle
								onClick={() => handleRemove(person.id)}
								style={{
									cursor: "pointer",
									color: "red",
									fontSize: "24px",
								}}
							/>
							<HiOutlinePencil
								onClick={() => showEditForm(person)}
								style={{
									cursor: "pointer",
									color: "green",
									fontSize: "24px",
									marginLeft: "10px",
								}}
							/>
						</div>
					))}
				</div>
			</div>
		</>
	);
};

export default Sidebar;
