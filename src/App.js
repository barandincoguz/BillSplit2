import React from "react";
import "./App.css";
import Sidebar from "./components/Sidebar";
import Process from "./components/Process";
import {Routes,Route} from "react-router-dom";
import Event from "./components/Event";

function App() {
	return (
		<div className="app">
			<Routes>
					<Route path="/event" element={<Event/>}></Route>

				<Route
					path="/"
					element={
						<>
							<Sidebar />
							<Process />
						</>
					}
				/>
			</Routes>


		</div>
	);
}

export default App;
