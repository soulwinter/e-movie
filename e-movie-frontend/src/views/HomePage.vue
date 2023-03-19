<template>
  <div>
    <h1>Welcome {{ username }}</h1>
    <p>Your token is: {{ token }}</p>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      token: this.$route.query.token,
      username: this.$route.query.username
    }
  }
}
</script>
