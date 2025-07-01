import { AppBar, Toolbar, Typography, Container } from "@mui/material";
import { useState } from "react";
import StrategyToggle from "./components/StrategyToggle.tsx";
import PostList from "./components/PostList.tsx";
import { STRATEGIES, Strategy } from "./types/strategy.ts";

function App() {
  const [strategy, setStrategy] = useState<Strategy>(STRATEGIES.PAGING);

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h4">게시판</Typography>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md">
        <StrategyToggle strategy={strategy} onChange={setStrategy} />
        <PostList strategy={strategy} />
      </Container>
    </>
  );
}

export default App;
