import axios from "axios";
import { PostApiResponse } from "../types/post";
import { Strategy } from "../types/strategy";

const BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

export const getPosts = async (
  page: number,
  strategy: Strategy
): Promise<PostApiResponse> => {
  const res = await axios.get(`${BASE_URL}/posts?type=${strategy}&page=${page}&size=10`);
  return res.data;
};
