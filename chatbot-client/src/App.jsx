import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Login from "./pages/Login/Login";
import SignUp from "./pages/SignUp/SignUp";
import Logout from "./pages/Logout/Logout";
import Home from "./pages/Home/Home";
import Chat from "./pages/Chat/Chat";

import './App.css'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />}/>
        <Route path="/signup" element={<SignUp />}/>
        <Route path="/logout" element={<Logout />}/>

        <Route path="/main" element={<Home />}/>
        <Route path="/chat/completions" element={<Chat />}/>
      </Routes>
    </Router>
  );
}

export default App