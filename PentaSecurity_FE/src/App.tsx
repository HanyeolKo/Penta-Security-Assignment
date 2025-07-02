import { AppBar, Toolbar, Typography, Container } from "@mui/material";
import { useState } from "react";
import StrategyToggle from "./components/StrategyToggle.tsx";
import PostList from "./components/PostList.tsx";
import { STRATEGIES, Strategy } from "./types/strategy.ts";
import { Box } from "@mui/material";

function App() {
  const [strategy, setStrategy] = useState<Strategy>(STRATEGIES.PAGING);

  return (
    <>
      <AppBar position="static" color="primary">
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h6" component="div">게시판</Typography>
          <Box>
            <StrategyToggle strategy={strategy} onChange={setStrategy} />
          </Box>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md">
        <PostList strategy={strategy} />
      </Container>
    </>
  );
}

export default App;
