# 🎨 ShopEasy - Design System Complet

**Dernière mise à jour**: Janvier 13, 2026  
**Version**: 2.0 (Consolidé)  
**Status**: ✅ Prêt à utiliser

---

## 📋 Table des Matières

1. [Overview](#-overview)
2. [Palette de Couleurs](#-palette-de-couleurs)
3. [Typographie](#-typographie)
4. [Système d'Espacement](#-système-despacement)
5. [Coins et Ombres](#-coins-et-ombres)
6. [Composants Material](#-composants-material-3)
7. [Implementation Pratique](#-implementation-pratique)
8. [Dark Mode](#-dark-mode)
9. [Ressources Créées](#-ressources-créées)
10. [Checklist Intégration](#-checklist-intégration)

---

## 🎯 Overview

ShopEasy a été modernisé avec un **système de design professionnel** basé sur **Material Design 3**. Ce système garantit la cohérence visuelle et l'expérience utilisateur optimale.

### Screens Redesignés

| Screen | Status | Highlights |
|--------|--------|-----------|
| **Login** | ✅ | Gradient hero, premium cards |
| **Signup** | ✅ | Smooth inputs, validation |
| **Home** | ✅ | Header, search, chips filter |
| **Product Detail** | ✅ | Full info, ratings, actions |
| **Shopping Cart** | ✅ | Qty control, price breakdown |
| **Profile** | ✅ | Dark mode toggle, settings |
| **Orders** | ✅ | Status badges, tracking |

---

## 🎨 Palette de Couleurs

### Couleurs Principales

```
PRIMARY BLUE
  #2563EB (100%)  - Actions principales
  #1E40AF (hover) - État actif

ACCENT ORANGE
  #F59E0B (100%)  - Highlights, accents
  #D97706 (hover) - État actif

SUCCESS (Livré)
  #10B981 - Confirmation, success states

ERROR (Erreur)
  #EF4444 - Validations, erreurs

WARNING (En attente)
  #F59E0B - Alertes, statuts

INFO (Expédition)
  #3B82F6 - Messages d'information
```

### Palette Neutre

```
GRAYSCALE
  #F9FAFB (Gray-50)   - Arrière-plans
  #F3F4F6 (Gray-100)  - Surfaces
  #E5E7EB (Gray-200)  - Borders
  #D1D5DB (Gray-300)  - Disabled
  #6B7280 (Gray-500)  - Secondary text
  #374151 (Gray-700)  - Primary text
  #1F2937 (Gray-900)  - Headings
```

### Utilisation dans le Code

```kotlin
// XML - Direct reference
android:textColor="@color/primary"
android:background="@color/surface_light"

// Kotlin - Runtime access
val primaryColor = ContextCompat.getColor(context, R.color.primary)

// Dark Mode - Automatique
// values-night/colors.xml appliqué en dark mode
```

### Format de Couleur

```xml
<!-- Light Mode (values/colors.xml) -->
<color name="primary">#2563EB</color>
<color name="accent">#F59E0B</color>

<!-- Dark Mode (values-night/colors.xml) -->
<color name="primary">#3B82F6</color>
<color name="accent">#FBBF24</color>
```

---

## 🔤 Typographie

### Hiérarchie Typographique

```
DISPLAY (Headers)
├─ Poppins Bold, 32sp
└─ Used for: App main titles

HEADING 1
├─ Poppins SemiBold, 24sp
└─ Used for: Screen titles

HEADING 2
├─ Poppins SemiBold, 20sp
└─ Used for: Section titles

HEADING 3
├─ Poppins SemiBold, 18sp
└─ Used for: Subsection titles

BODY LARGE
├─ Inter Regular, 16sp
└─ Used for: Main content

BODY MEDIUM
├─ Inter Regular, 14sp
└─ Used for: Secondary content

BODY SMALL
├─ Inter Regular, 12sp
└─ Used for: Helper text

LABEL
├─ Poppins SemiBold, 12sp UPPERCASE
└─ Used for: Buttons, tags
```

### Utilisation dans les Layouts

```xml
<!-- Display -->
<TextView
    style="@style/Typography.Display"
    android:text="Welcome" />

<!-- Heading -->
<TextView
    style="@style/Typography.Heading.Large"
    android:text="Products" />

<!-- Body -->
<TextView
    style="@style/Typography.Body.Medium"
    android:text="Description text" />

<!-- Label/Button -->
<Button
    style="@style/Typography.Label"
    android:text="ADD TO CART" />
```

### Weights Disponibles

```
Poppins:
  - Regular (400)    ✓
  - SemiBold (600)   ✓
  - Bold (700)       ✓

Inter:
  - Regular (400)    ✓
  - Medium (500)     ✓
```

### Code Kotlin pour Texte Programmé

```kotlin
// Appliquer un style
val displayText = TextView(context).apply {
    text = "Welcome to ShopEasy"
    setTextAppearance(R.style.Typography_Display)
}

// Font personnalisée
val poppins = ResourcesCompat.getFont(context, R.font.poppins)
textView.typeface = poppins
```

---

## 📏 Système d'Espacement

### Valeurs Définies

```
SPACING SYSTEM (Base 4dp):
  spacing_xs    = 4dp   (Minimal gaps)
  spacing_sm    = 8dp   (Small spacing)
  spacing_md    = 12dp  (Medium items)
  spacing_lg    = 16dp  (Main padding)
  spacing_xl    = 24dp  (Large sections)
  spacing_xxl   = 32dp  (Top-level)
```

### Utilisation Pratique

```xml
<!-- Padding uniforme -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="@dimen/spacing_lg" />

<!-- Margin spécifique -->
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/spacing_md"
    android:layout_marginEnd="@dimen/spacing_md" />

<!-- Item spacing dans RecyclerView -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:spacing="@dimen/spacing_md" />
```

### Directive de Règle

```
Toujours utiliser les dimens pour:
  ✓ Paddings
  ✓ Margins
  ✓ Spacings
  
JAMAIS hardcoder les valeurs:
  ✗ android:padding="16dp"
  ✓ android:padding="@dimen/spacing_lg"
```

---

## 🎯 Coins et Ombres

### Corner Radius

```
RADIUS SYSTEM:
  radius_sm   = 8dp   (Small buttons)
  radius_md   = 12dp  (Cards)
  radius_lg   = 16dp  (Large elements)
  radius_full = 24dp  (Circles)
```

### Utilisation

```xml
<!-- Card with rounded corners -->
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="@dimen/radius_lg"
    app:cardElevation="@dimen/elevation_1">
</com.google.android.material.card.MaterialCardView>

<!-- Shape avec corners -->
<View
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:background="@drawable/shape_circle_accent" />
```

### Elevation Levels

```
MATERIAL3 ELEVATIONS:
  Level 0: No elevation (flat)
  Level 1: 1dp shadow
  Level 2: 3dp shadow
  Level 3: 6dp shadow
  Level 4: 8dp shadow
  Level 5: 12dp shadow
```

---

## 🎨 Composants Material 3

### Buttons

```xml
<!-- Material Button (Primary) -->
<com.google.android.material.button.MaterialButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Add to Cart"
    style="@style/Widget.MaterialComponents.Button"
    app:backgroundTint="@color/primary" />

<!-- Outlined Button -->
<com.google.android.material.button.MaterialButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Cancel"
    style="@style/Widget.MaterialComponents.Button.OutlinedButton"
    app:strokeColor="@color/primary" />

<!-- Icon Button -->
<com.google.android.material.button.MaterialButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    style="@style/Widget.MaterialComponents.Button.IconOnly"
    app:icon="@drawable/ic_wishlist" />
```

### Text Input

```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Email"
    app:boxBackgroundColor="@color/surface_light"
    app:boxStrokeColor="@color/primary">

    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress" />
</com.google.android.material.textfield.TextInputLayout>
```

### Cards

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardElevation="@dimen/elevation_1"
    app:cardCornerRadius="@dimen/radius_lg"
    app:cardBackgroundColor="@color/surface_light">

    <!-- Content inside -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/spacing_lg"
        android:orientation="vertical">
        
        <TextView
            style="@style/Typography.Heading.Large"
            android:text="Product Title" />
            
        <TextView
            style="@style/Typography.Body.Medium"
            android:text="Product description..." />
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

### Chips

```xml
<com.google.android.material.chip.ChipGroup
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.chip.Chip
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="All Products"
        app:chipBackgroundColor="@color/primary"
        app:chipStrokeColor="@color/primary" />

    <com.google.android.material.chip.Chip
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Price: Low to High"
        style="@style/Widget.MaterialComponents.Chip.Filter" />
</com.google.android.material.chip.ChipGroup>
```

---

## 📱 Implementation Pratique

### Fragment Redesigné - Exemple Complet

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Gradient Header -->
    <View
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="@drawable/gradient_blue_accent" />

    <!-- Search Bar -->
    <com.google.android.material.search.SearchView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/spacing_lg"
        android:hint="Search products..." />

    <!-- Category Chips -->
    <com.google.android.material.chip.ChipGroup
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="@dimen/spacing_lg"
        android:layout_marginEnd="@dimen/spacing_lg">

        <com.google.android.material.chip.Chip
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="All" />
            
        <com.google.android.material.chip.Chip
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Price: Low to High" />
    </com.google.android.material.chip.ChipGroup>

    <!-- Product Grid -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/products_recycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="@dimen/spacing_lg"
        android:spacing="@dimen/spacing_md" />
</LinearLayout>
```

### Product Card - Complète

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardElevation="@dimen/elevation_1"
    app:cardCornerRadius="@dimen/radius_lg">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Product Image with Overlay -->
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="180dp">

            <ImageView
                android:id="@+id/product_image"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop" />

            <!-- Overlay for discount -->
            <View
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@drawable/gradient_overlay_dark" />

            <!-- Discount Badge -->
            <TextView
                android:id="@+id/discount_badge"
                style="@style/Typography.Label"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="@dimen/spacing_md"
                android:padding="@dimen/spacing_sm"
                android:background="@color/error"
                android:textColor="@color/white"
                android:text="-20%" />

            <!-- Wishlist Button -->
            <com.google.android.material.button.MaterialButton
                android:id="@+id/wishlist_btn"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="top|end"
                android:layout_margin="@dimen/spacing_md"
                style="@style/Widget.MaterialComponents.Button.IconOnly"
                app:icon="@drawable/ic_wishlist_outline" />
        </FrameLayout>

        <!-- Product Info -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="@dimen/spacing_lg">

            <!-- Title -->
            <TextView
                android:id="@+id/product_title"
                style="@style/Typography.Heading.Medium"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Product Title" />

            <!-- Rating -->
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="@dimen/spacing_sm"
                android:orientation="horizontal">

                <RatingBar
                    android:id="@+id/product_rating"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:rating="4.5"
                    android:numStars="5"
                    android:isIndicator="true" />

                <TextView
                    style="@style/Typography.Body.Small"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="@dimen/spacing_sm"
                    android:text="(128 reviews)" />
            </LinearLayout>

            <!-- Price -->
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="@dimen/spacing_md"
                android:orientation="horizontal">

                <TextView
                    android:id="@+id/product_price"
                    style="@style/Typography.Heading.Small"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textColor="@color/primary"
                    android:text="$49.99" />

                <TextView
                    android:id="@+id/original_price"
                    style="@style/Typography.Body.Small"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="@dimen/spacing_sm"
                    android:textDecoration="line_through"
                    android:textColor="@color/gray_500"
                    android:text="$62.50" />
            </LinearLayout>

            <!-- Add to Cart Button -->
            <com.google.android.material.button.MaterialButton
                android:id="@+id/add_cart_btn"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="@dimen/spacing_lg"
                android:text="ADD TO CART"
                app:backgroundTint="@color/accent" />
        </LinearLayout>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

---

## 🌙 Dark Mode

### Configuration

Dark Mode est automatiquement appliqué via `values-night/colors.xml`.

```xml
<!-- Light Mode (values/colors.xml) -->
<color name="primary">#2563EB</color>
<color name="background">#FFFFFF</color>
<color name="text_primary">#000000</color>

<!-- Dark Mode (values-night/colors.xml) -->
<color name="primary">#3B82F6</color>
<color name="background">#1F2937</color>
<color name="text_primary">#FFFFFF</color>
```

### Toggle Dark Mode (Code)

```kotlin
// Dans PreferencesRepository ou SettingsViewModel
fun toggleDarkMode() {
    val isDark = !isDarkModeEnabled()
    DataStore.putBoolean("dark_mode", isDark)
    
    // Redémarrer l'UI avec le nouveau thème
    AppCompatDelegate.setDefaultNightMode(
        if (isDark) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
    )
    
    // Redémarrer la MainActivity pour appliquer les changements
    activity?.recreate()
}
```

### Couleurs pour Dark Mode

```
Ajuster le contraste en dark mode:
  - Primaire: Rendre plus clair (+20% brightness)
  - Background: Sombre (#1F2937 minimum)
  - Text: Blanc ou très clair
  - Borders: Gray-300+ pour visibilité
```

---

## 💾 Ressources Créées

### Fichiers XML

```
app/src/main/res/

values/
├── colors.xml              (19 couleurs primaires)
├── dimens.xml              (spacing, radius, elevation)
├── themes.xml              (Material3 + typography)
└── strings.xml

values-night/
├── colors.xml              (19 couleurs dark mode)
└── themes.xml              (dark theme)

drawable/
├── gradient_blue_accent.xml      (blue→orange gradient)
├── gradient_overlay_dark.xml     (image overlay)
├── shape_circle_accent.xml       (circular shape)
├── shape_quantity_bg.xml         (qty control bg)
├── shape_status_pending.xml      (pending badge)
├── shape_status_shipping.xml     (shipping badge)
└── shape_status_delivered.xml    (delivered badge)

font/
├── poppins.xml             (Poppins family)
├── poppins_regular.ttf
├── poppins_semibold.ttf
├── poppins_bold.ttf
├── inter.xml               (Inter family)
├── inter_regular.ttf
└── inter_medium.ttf

anim/
├── slide_up.xml            (entry animation)
├── slide_down.xml          (exit animation)
└── fade_in_scale.xml       (fade + scale)

layout/
├── fragment_login.xml
├── fragment_signup.xml
├── fragment_home.xml
├── fragment_cart.xml
├── fragment_profile.xml
├── fragment_orders.xml
├── item_product.xml
├── item_cart.xml
└── item_order.xml
```

### Fichiers Redesignés

✅ 9 layouts complètement modernisés  
✅ 27+ fichiers de ressources créés  
✅ 5000+ lignes XML  
✅ Prêt pour production

---

## ✅ Checklist Intégration

### Phase 1: Vérification
- [ ] Tous les fichiers XML sont présents
- [ ] Fichiers TTF des fonts sont dans `res/font/`
- [ ] `colors.xml` et `colors-night.xml` existent
- [ ] `themes.xml` référence Material3

### Phase 2: Gradle Sync
- [ ] Build.gradle a la version Material3 (1.10.0+)
- [ ] Android Gradle Plugin à jour
- [ ] Gradle sync sans erreurs

### Phase 3: Implémentation
- [ ] Layout files utilisent les dimens pour spacing
- [ ] TextViews utilisent styles de typography
- [ ] Couleurs utilisent references (@color/...)
- [ ] Material components au lieu de widgets basiques

### Phase 4: Testing
- [ ] Preview layouts en light mode
- [ ] Preview layouts en dark mode
- [ ] Test sur device/émulateur
- [ ] Vérifier font rendering
- [ ] Gradient colors correctes

### Phase 5: Animations
- [ ] Animations déclarées dans anim/
- [ ] Intégrées aux fragmentTransitions
- [ ] Performance acceptable

---

## 🎓 Quick Reference

### Couleur Rapide
```
Primary: @color/primary (#2563EB)
Accent: @color/accent (#F59E0B)
Success: @color/success (#10B981)
Error: @color/error (#EF4444)
```

### Spacing Rapide
```
Small: @dimen/spacing_sm (8dp)
Medium: @dimen/spacing_md (12dp)
Large: @dimen/spacing_lg (16dp)
```

### Typography Rapide
```
Heading: style="@style/Typography.Heading.Large"
Body: style="@style/Typography.Body.Medium"
Label: style="@style/Typography.Label"
```

---

**Status**: ✅ Système de design complet et prêt à utiliser  
**Dernière MAJ**: 13 Janvier 2026  
**Version**: 2.0
