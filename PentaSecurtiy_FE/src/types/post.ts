export interface Post{
    id: number;
    title: string;
    content: string;
    author: string;
    createAt: string;
}

export interface PagingResponse{
    posts: Post[];
    pageNumber: number;
    pageSize: number;
    totalPages: number;
    totalElements: number;
    isFirst: boolean;
    isLast: boolean;
}

export interface InfiniteResponse{
    posts: Post[];
    hasMore: boolean;
}

export type PostApiResponse = PagingResponse | InfiniteResponse;