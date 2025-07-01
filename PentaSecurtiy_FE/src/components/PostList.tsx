import { useEffect, useRef, useState } from "react";
import { Post } from "../types/post.ts";
import { getPosts } from "../api/post.ts";
import PostCard from "./PostCard.tsx";
import { STRATEGIES, Strategy } from "../types/strategy.ts";
import {
  Box,
  CircularProgress,
  Pagination,
} from "@mui/material";

export default function PostList({ strategy }: { strategy: Strategy }) {
  const [posts, setPosts] = useState<Post[]>([]);
  const [page, setPage] = useState(-1);       // -1로 시작해서 최초 진입 감지
  const [totalPages, setTotalPages] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);

  const observerRef = useRef<HTMLDivElement | null>(null);

  const loadPosts = async () => {
    if (loading) return;
    if (strategy === STRATEGIES.INFINITY && !hasMore) return;

    setLoading(true);
    try {
      const res = await getPosts(page, strategy);

      switch(strategy){
        case STRATEGIES.PAGING :
          setPosts(res.posts);
          if ("totalPages" in res) {
            setTotalPages(res.totalPages ?? 1);
          }
          break;

        case STRATEGIES.INFINITY :
          setPosts((prev) => [...prev, ...res.posts]);
          if ("hasMore" in res) {
            setHasMore(res.hasMore);
          }
        break;
      }
    } catch (e) {
      console.error("API 요청 실패", e);
      alert("게시글을 불러오지 못했습니다.");
    } finally {
      setLoading(false);
    }
  };

  // 전략 변경 시 초기화
  useEffect(() => {
    setPosts([]);
    setTotalPages(0);
    setHasMore(true);
    setPage(-1); // -1에서 0으로 올려 트리거
  }, [strategy]);

  // page 변경 시 loadPosts
  useEffect(() => {
    if (page === -1) {
      setPage(0);
      return;
    }
    loadPosts();
  }, [page]);

  // 무한스크롤 옵저버
  useEffect(() => {
    if (strategy !== STRATEGIES.INFINITY) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          setPage((prev) => prev + 1);
        }
      },
      { threshold: 1 }
    );

    const target = observerRef.current;
    if (target) observer.observe(target);

    return () => {
      if (target) observer.unobserve(target);
    };
  }, [strategy, hasMore, page]);

  return (
    <Box sx={{ padding: 2 }}>
      {posts.map((post) => (
        <PostCard key={post.id} post={post} />
      ))}

      {/* 페이징 전략일 경우 페이지 블럭 표시 */}
      {strategy === STRATEGIES.PAGING && totalPages > 0 && (
        <Box display="flex" justifyContent="center" sx={{ mt: 2 }}>
          <Pagination
            count={totalPages}
            page={page + 1}
            onChange={(_, value) => setPage(value - 1)}
            color="primary"
          />
        </Box>
      )}

      {/* 무한스크롤 하단 로딩 및 메시지 */}
      {strategy === STRATEGIES.INFINITY && (
        <Box
          ref={observerRef}
          sx={{ height: 60, textAlign: "center", mt: 2 }}
        >
          {loading && <CircularProgress />}
          {!loading && !hasMore && <span>모든 글을 불러왔습니다</span>}
        </Box>
      )}
    </Box>
  );
}
