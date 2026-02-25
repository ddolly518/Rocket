import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Login from "./pages/Login/Login";
import SignUp from "./pages/SignUp/SignUp";
import Logout from "./pages/Logout/Logout";
import Home from "./pages/Home/Home";
import Chat from "./pages/Chat/Chat";
import Conversations from "./pages/Conversations/Conversations";
import Conversation from "./pages/Conversation/Conversation";
import Messages from "./pages/Messages/Messages";
import Delete from "./pages/Delete/Delete";
import Stream from "./pages/Stream/Stream";

import './App.css'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />}/>
        <Route path="/login" element={<Login />}/>
        <Route path="/signup" element={<SignUp />}/>
        <Route path="/logout" element={<Logout />}/>

        <Route path="/main" element={<Home />}/>
        <Route path="/chat/completions" element={<Chat />}/>
        <Route path="/conversations" element={<Conversations />}/>
        <Route path="/conversation" element={<Conversation />}/>
        <Route path="/messages" element={<Messages />}/>
        <Route path="/delete" element={<Delete />}/>
        <Route path="/stream" element={<Stream />}/>
      </Routes>
    </Router>
  );
}

export default App