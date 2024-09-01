import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./event.css";
import { HiMinusCircle } from "react-icons/hi";
import { HiOutlinePencil } from "react-icons/hi";

const Event = () => {
    const [eventList, setEventList] = useState([]);
    const [name, setName] = useState("");
    const [date, setDate] = useState("");
    const [error, setError] = useState("");
    const dateRegex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;

    const fetchEventList = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/event/getAllEvents");
            if (response && response.data) {
                setEventList(response.data);  // Listeyi güncelle
                console.log("Liste getirildi", response.data);
            }
        } catch (error) {
            console.log("Veri çekme hatası:", error);
        }
    };

    useEffect(() => {
        fetchEventList();
    }, []);

    const addEventOnSubmit = async () => {
        if (!name || !date) {
            console.log("Name and date are required.");
            setError("Etkinlik adı ve Tarihini giriniz ")
            return;
        }
        if (!dateRegex.test(date)) {
            setError("Tarih formatı geçerli değil. Lütfen DD/MM/YYYY formatında girin.");
            return;
        }
        const newEvent = { name, date };
        try {
            const response = await axios.post("http://localhost:8080/api/event/createEvent", newEvent);
            console.log("Yeni etkinlik eklendi:", response.data);
            fetchEventList();  // Etkinliği ekledikten sonra listeyi güncelle
        } catch (error) {
            console.log("Etkinlik ekleme hatası:", error);
        }
    };

    return (
        <div className="EventFormAndList">
            <div className="eventForm">
                <label htmlFor="name">Etkinlik Adı</label>
                <input
                
                    id="name"
                    type="text"
                    onChange={(e) => setName(e.target.value)}
                />
                <label htmlFor="tarih">Tarih</label>
                <input
                    
                    id="tarih"
                    type="text"
                    onChange={(e) => setDate(e.target.value)}
                />
                
                <input type="button" id="btn" onClick={addEventOnSubmit} value="oluştur" />
                
                {error && <p className='error'>{error}</p>}
            </div>
            <div className="listEventsContainer">
                {eventList.map((event, index) => (
                <div key={index} className='listEvents'>
                        {event.name} 
                        <br /> 
                        {event.date} 
                        <div className="operations">
                <HiMinusCircle
                    style={{
                        cursor: "pointer",
                        color: "red",
                        fontSize: "24px",
                    }}
                />
                <HiOutlinePencil
                    style={{
                        cursor: "pointer",
                        color: "green",
                        fontSize: "24px",
                        marginLeft: "10px",
                    }}
                />
                </div>
                </div>
                ))}
            </div>
        </div>
    );
};

export default Event;
