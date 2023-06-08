import React from 'react';
import { Nav } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';

const Navbar = () => {
  const location = useLocation();

  const isCurrentPage = (path) => {
    return location.pathname === path ? 'active' : '';
  }

  return (
    <Nav activeKey={location.pathname}>
      <Nav.Item as={Link} to="/political-bias" className={`nav-link ${isCurrentPage("/political-bias")}`}>
        Political Bias
      </Nav.Item>
      <Nav.Item as={Link} to="/article-tonality" className={`nav-link ${isCurrentPage("/article-tonality")}`}>
        Article Tonality
      </Nav.Item>
      <Nav.Item as={Link} to="/article-keywords" className={`nav-link ${isCurrentPage("/article-keywords")}`}>
        Article Keywords
      </Nav.Item>
    </Nav>
  );
};

export default Navbar