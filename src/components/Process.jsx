import React, { useEffect, useState } from "react";
import "./process.css";
import axios from "axios";
import { TiTick } from "react-icons/ti";

const Process = () => {
	const [processList, setProcessList] = useState([]);
	const [clickedIndexes,setClickedIndexes ] = useState([]);


	const fetchProcessList = async () => {
		try {
			const getProcessList = await axios.get(
				"http://localhost:8080/api/person/process"
			);
			setProcessList(getProcessList.data);
		} catch (error) {
			console.error("Fetching failed", error);
		}
	};

	const handleClick = (index) => {
		if (clickedIndexes.includes(index)){
			setClickedIndexes(clickedIndexes.filter(i=>i!==index))
			console.log(clickedIndexes)
		}
		else {
			setClickedIndexes([...clickedIndexes,index]);
		}
	}
	useEffect(() => {
		handleCalc();
	}, []);
	const handleCalc = () => {
		fetchProcessList();
	}


	useEffect(() => {
		console.log(processList);
	}, [processList]); // processList değiştiğinde konsola yaz

	return (
		<div>
			<div className="wrapper">
				<div style={{ display: "flex", flexDirection: "row"}}>
					{processList.slice(0, 2).map((msg, index) => (
						<div key={index} className="processCard" style={{
							backgroundColor: "#f0e5d8",
							width: "100%",
							transition: "transform 0.3s ease" }}
							 onMouseEnter={(e) => e.currentTarget.style.transform = "scale(1.05)"}
							 onMouseLeave={(e) => e.currentTarget.style.transform = "scale(1)"}>
							<p className="pText">{msg}</p>
						</div>
					))}
				</div>

				{processList.slice(2).map((msg, index) => (
					<div key={index} className="processCard" >
						<p className="pText">{msg}</p>
						<TiTick className="tick"
						onClick={
							() => handleClick(index)
						}
								style={{color: clickedIndexes.includes(index) ? "green" : "red" }}></TiTick>
					</div>
				))}
			</div>

			<button type="submit" id="hesapla" onClick={handleCalc}>
				Hesapla
			</button>
		</div>
	);
};

export default Process;
