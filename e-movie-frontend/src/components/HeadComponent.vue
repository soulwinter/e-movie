<template>
    <header class="header">
      <h1 class="title">e-movie Movie Information Retrieval System</h1>
    </header>
  </template>
  
  <script>
  export default {
    name: 'HeadComponent',
  };
  </script>
  
  <style scoped>
  .header {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #2196f3;
    height: 60px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .title {
    font-family: 'Roboto', sans-serif;
    font-size: 24px;
    font-weight: bold;
    color: #ffffff;
    margin: 0;
  }
  </style>
  