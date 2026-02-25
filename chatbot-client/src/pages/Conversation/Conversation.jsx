import { useState } from "react";
import api from "../../api/axiosInstance";

import "./Conversation.css";

export const Conversation = () => {
  const [inputId, setInputId] = useState("");
  const [conversation, setConversation] = useState(null);

  const fetchConversation = async () => {
    if (inputId.trim() === "") return;

    try {
      const response = await api.get(`/conversations/${Number(inputId)}`);
      setConversation(response.data?.data ?? null);
    } catch (err) {
      console.error(
          "대화 상세 조회 실패:",
          err.response?.data || err.message,
        );
    }
  };

  return (
    <div className="conversation-detail">
      <h2>대화 상세 조회</h2>

      <div className="input-section">
        <input
          type="number"
          placeholder="Conversation ID 입력"
          value={inputId}
          onChange={(e) => setInputId(e.target.value)}
        />
        <button onClick={fetchConversation}>조회</button>
      </div>

      {conversation && (
        <div className="detail-card">
          <p><strong>ID:</strong> {conversation.id}</p>
          <p><strong>Title:</strong> {conversation.title}</p>
          <p><strong>Message Count:</strong> {conversation.count}</p>
          <p><strong>Created At:</strong> {conversation.createdAt}</p>
          <p><strong>Updated At:</strong> {conversation.updatedAt}</p>
        </div>
      )}
    </div>
  );
};

export default Conversation;