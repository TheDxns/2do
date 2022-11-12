<template>
  <v-footer
      absolute
      dark
      padless
  >
    <v-card
        class="flex"
        flat
        tile
    >
      <v-card-title class="font-weight-light">
        <strong class="subheading"></strong>
        <v-spacer></v-spacer>
        <v-card-text class="py-0  white--text text-center">
        © {{ new Date().getFullYear() }} — <strong>Grupa H</strong>
        </v-card-text>
      </v-card-title>
    </v-card>
  </v-footer>
</template>
<script>
export default {
  name: 'Navbar',
}
</script>