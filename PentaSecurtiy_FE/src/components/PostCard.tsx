import { Card, CardContent, Typography } from "@mui/material";
import { Post } from "../types/post.ts";

export default function PostCard({ post }: { post: Post }) {
  return (
    <Card sx={{ marginBottom: 2 }}>
      <CardContent>
        <Typography variant="h6">{post.title}</Typography>
        <Typography variant="body2" color="text.secondary">
          {post.author} | {new Date(post.createAt).toLocaleString()}
        </Typography>
        <Typography variant="body1" sx={{ marginTop: 1 }}>
          {post.content}
        </Typography>
      </CardContent>
    </Card>
  );
}
