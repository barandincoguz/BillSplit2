import React from "react";

import { Navigate, Route, Routes } from "react-router-dom";
import Event from "./components/Event";
import EventDetails from "./components/EventDetails";
import Error from "./components/Error";
function App() {
	return (
		<div className="app">
			<Routes>
				<Route path="/" element={<Navigate to="/event" />} />
				<Route path="/event" element={<Event />}></Route>
				<Route
					path="/event/:eventId"
					element={<EventDetails></EventDetails>}
				/>
				<Route path="/error" element={<Error></Error>}></Route>
				<Route path="*" element={<Error />} />
			</Routes>
		</div>
	);
}

export default App;
