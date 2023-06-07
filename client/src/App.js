import { Container } from "react-bootstrap";
import { Route, Routes } from "react-router-dom";
import "App.css";
import Navbar from "components/Navbar";
import Particles from "components/Particles";
import ArticleKeywords from "pages/ArticleKeywords";
import ArticleTonality from "pages/ArticleTonality";
import Home from "pages/Home";
import PoliticalBias from "pages/PoliticalBias";
import LocalResources from "resources/LocalResources";

function App() {
  return (
    <div className="App">
      <Navbar />
      <Container>
        <Routes>
          <Route path="/" index element={<Home />} />
          <Route path="/political-bias" element={<PoliticalBias />} />
          <Route path="/article-tonality" element={<ArticleTonality />} />
          <Route path="/article-keywords" element={<ArticleKeywords />} />
        </Routes>
      </Container>
      <Particles />
    </div>
  );
}

export default App;
