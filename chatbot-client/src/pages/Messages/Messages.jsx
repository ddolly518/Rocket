import { useState } from "react";
import api from "../../api/axiosInstance";

import "./Messages.css";

export const Messages = () => {
  const [inputId, setInputId] = useState("");
  const [messages, setMessages] = useState([]);

  const fetchMessages = async () => {
    if (inputId.trim() === "") return;

    setMessages([]);

    try {
      const response = await api.get(
        `/conversations/${Number(inputId)}/messages`,
      );

      setMessages(response.data?.data ?? []);
    } catch (err) {
      console.error(
        "채팅 메시지 전송 실패:",
        err.response?.data || err.message,
      );
    }
  };

  return (
    <div className="messages">
      <h2>대화 메시지 조회</h2>

      <div className="input-section">
        <input
          type="number"
          placeholder="Conversation ID 입력"
          value={inputId}
          onChange={(e) => setInputId(e.target.value)}
        />
        <button onClick={fetchMessages}>조회</button>
      </div>

      {messages.length > 0 && (
        <ul className="message-list">
          {messages.map((msg) => (
            <li key={msg.id} className={`message-item ${msg.role}`}>
              <div>
                <strong>Message ID:</strong> {msg.id}
              </div>
              <div>
                <strong>Conversation ID:</strong> {msg.conversationId}
              </div>
              <div>
                <strong>Role:</strong> {msg.role}
              </div>
              <div>
                <strong>Content:</strong> {msg.content}
              </div>
              <div>
                <strong>Created At:</strong> {msg.createdAt}
              </div>
            </li>
          ))}
        </ul>
      )}

    </div>
  );
};

export default Messages;
