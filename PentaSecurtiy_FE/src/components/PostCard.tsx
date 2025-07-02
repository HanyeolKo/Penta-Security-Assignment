import { Card, CardContent, Typography, Box } from "@mui/material";
import { Post } from "../types/post.ts";

export default function PostCard({ post }: { post: Post }) {
  return (
    <Card
      variant="outlined"
      sx={{ mb: 2, '&:hover': { boxShadow: 3, }, }}
    >
      <CardContent>
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Typography variant="h6" fontWeight="bold">
            {post.title}
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ ml: 2 }}>
            {new Date(post.createAt).toLocaleString()}
          </Typography>
        </Box>

        <Typography variant="subtitle2" color="text.secondary" sx={{ mt: 0.5 }}>
          작성자: {post.author}
        </Typography>

        <Typography variant="body1" sx={{ mt: 1 }}>
          {post.content}
        </Typography>
      </CardContent>
    </Card>
  );
}
