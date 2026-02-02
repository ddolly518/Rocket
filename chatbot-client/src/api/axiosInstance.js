// src/api/axiosInstance.js
import axios from 'axios';
import qs from 'qs';

/** ------------------------------------------------------------------
 *  공통 axios 인스턴스
 *    · baseURL : /api
 *    · JSON 자동 파싱 (Content-Type은 요청 시 직접 지정)
 *    · 요청 시 인증 토큰(예: JWT) 헤더 부착(선택)
 *    · 에러 로깅 & 공통 오류 메시지 처리
 * ----------------------------------------------------------------- */

// 공통 설정을 가진 axios 객체 생성
const api = axios.create({
    baseURL: '/api', // 모든 요청은 자동으로 /api로 시작
    //withCredentials: true,       // 쿠키 필요 시
    // 기본 headers에서 'Content-Type' 제거!
    paramsSerializer: params =>
        qs.stringify(params, {arrayFormat: 'repeat'}), // 쿼리 파라미터를 서버 친화적인 URL 문자열로 만들어줌
});

/* ----- 요청 인터셉터 : 토큰 주입 (옵션) ---------------------------- */
api.interceptors.request.use((config) => {
    // 토큰 안 붙일 경로 목록
    const skipAuthUrls = ['/auth/login', '/auth/signup'];

    // 요청 URL 가져오기 (baseURL 포함 여부 고려)
    const url = config.url || '';
    const shouldSkipToken = skipAuthUrls.some(path => url.endsWith(path));

    // 토큰 주입
    if (!shouldSkipToken) {
        const token = localStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
    }
    return config;
}, (error) => Promise.reject(error));

/* ----- 응답 인터셉터 : 공통 에러 처리 ----------------------------- */
api.interceptors.response.use(
    (res) => res, // 성공 → 그대로 반환
    (err) => { // 실패 → 에러 로그 출력 후 다시 던짐
        console.error('[API ERROR]', err.response?.data || err.message);
        return Promise.reject(err);
    }
);

export default api;